package cn.aaron911.log.server.monitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.core.dto.RunLogMessage;
import cn.aaron911.log.core.dto.WarningRule;
import cn.aaron911.log.core.redis.RedisClient;
import cn.aaron911.log.server.cache.AppNameCache;
import cn.aaron911.log.server.util.ElasticLowerClientSingleton;

/**
 * description： 日志监控报警
 */
@Component
public class LogMonitorListener implements ApplicationListener<LogMonitorEvent> {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LogMonitorListener.class);

    @Autowired
    private LogMonitorRuleConfig logMonitorRuleConfig;

    @Autowired
    private RedisClient redisClient;

    private static final String WARNING_NOTICE = ":WARNING:NOTICE";

    @Value("${aaron911.log.ui.url:127.0.0.1:8989}")
    private String url;

    /**
     * 当KEY设置过期时间时加的后缀
     */
    private static final String KEY_NX = ":NX";



    @Autowired
    private ElasticLowerClientSingleton elasticLowerClient;


    @Async
    @Override
    public void onApplicationEvent(LogMonitorEvent event) {
        List<String> logs = event.getLogs();
        List<RunLogMessage> runlogs = new ArrayList<>();
        logs.forEach(logString -> {
            RunLogMessage runLogMessage = JSON.parseObject(logString, RunLogMessage.class);
            AppNameCache.appName.add(runLogMessage.getAppName());
            if (runLogMessage.getLogLevel().toUpperCase().equals("ERROR")) {
                runlogs.add(runLogMessage);
            }
        });
        //解析日志
        parserLogMessage(runlogs);
    }

    /**
     * 解析日志
     *
     * @param logMessages 日志
     */
    public void parserLogMessage(List<RunLogMessage> logMessages) {
        logMessages.forEach(runLogMessage -> {
            List<WarningRule> monitorRuleConfig = logMonitorRuleConfig.getMonitorRuleConfig(runLogMessage.getAppName());
            if (monitorRuleConfig != null) {
                //运行规则
                enforcementRules(monitorRuleConfig, runLogMessage);
            }
        });

    }

    /**
     * 执行日志监控规则
     *
     * @param rules         规则
     * @param runLogMessage 日志
     */
    public void enforcementRules(List<WarningRule> rules, RunLogMessage runLogMessage) {
        for (int i = 0; i < rules.size(); i++) {
            WarningRule warningRule = rules.get(i);
            String className = warningRule.getClassName();
            String appName = warningRule.getAppName();
            if (!StringUtils.isEmpty(className)
                    && !className.equals(runLogMessage.getClassName())) {
                continue;
            }
            //统计分析
            statisticAlnalysis(getKey(appName, className), warningRule);
        }
    }


    /**
     * 统计分析
     *
     * @param key  缓存key
     * @param rule 规则
     */
    private void statisticAlnalysis(String key, WarningRule rule) {
        String time = redisClient.hget(key, LogMessageConstant.LOG_MONITOR_KEY_MAP_FILED_TIME);
        if (StringUtils.isEmpty(time)) {
            time=String.valueOf(System.currentTimeMillis());
            redisClient.hset(key, LogMessageConstant.LOG_MONITOR_KEY_MAP_FILED_TIME, time);
        }
        long startTime = Long.parseLong(time);
        long endTime = startTime + (rule.getTime() * 1000);
        if (endTime > System.currentTimeMillis()) {
            Long incr = redisClient.hincrby(key, LogMessageConstant.LOG_MONITOR_KEY_MAP_FILED_COUNT, 1);
            if (incr >= rule.getErrorCount() && !redisClient.existsKey(key + WARNING_NOTICE)) {
                earlyWarning(rule, incr, key);
                redisClient.del(key);
            }
        } else {
            redisClient.hdel(key, LogMessageConstant.LOG_MONITOR_KEY_MAP_FILED_TIME,
                    LogMessageConstant.LOG_MONITOR_KEY_MAP_FILED_COUNT);
            redisClient.hincrby(key, LogMessageConstant.LOG_MONITOR_KEY_MAP_FILED_COUNT, 1);
            redisClient.hset(key, LogMessageConstant.LOG_MONITOR_KEY_MAP_FILED_TIME,
                    String.valueOf(System.currentTimeMillis()));
        }

    }

    /**
     * 组装key
     *
     * @param appName   应用名
     * @param className 类名
     * @return
     */
    private static String getKey(String appName, String className) {
        String key = LogMessageConstant.LOG_MONITOR_KEY + appName;
        if (!StringUtils.isEmpty(className)) {
            key = key + ":" + className;
        }
        return key;
    }

    /**
     * 执行预警
     *
     * @param rule
     * @param count
     * @param key
     */
    private void earlyWarning(WarningRule rule, long count, String key) {
        LogMonitorTextMessage logMonitorTextMessage =
                new LogMonitorTextMessage.Builder(rule.getAppName())
                        .className(rule.getClassName())
                        .errorCount(rule.getErrorCount())
                        .time(rule.getTime())
                        .count(count)
                        .monitorUrl(getMonitorMessageURL(rule))
                        .build();
        if (!StringUtils.isEmpty(rule.getReceiver())) {
            String[] split = rule.getReceiver().split(",");
            List<String> receivers = new ArrayList<String>(Arrays.asList(split));
            if (receivers.contains("all") || receivers.contains("ALL")) {
                logMonitorTextMessage.setAtAll(true);
               receivers.remove("all");
                receivers.remove("ALL");
            }
            logMonitorTextMessage.setAtMobiles(receivers);
        }
        String warningKey = key + WARNING_NOTICE;
        if (redisClient.setNx(warningKey + KEY_NX, rule.getTime())) {
            logger.info(logMonitorTextMessage.getText());
            if (rule.getHookServe() == 1) {
            	WechatClient.sendToWeChat(logMonitorTextMessage, rule.getWebhookUrl());
            } else {
            	logger.info("还没实现");
            }
            sendMesageES(rule, count);
        }
        redisClient.set(warningKey, warningKey);
        redisClient.expireAt(warningKey, Long.parseLong(String.valueOf(rule.getTime())));
    }


    /**
     * 报警记录加入至ES
     */
    private void sendMesageES(WarningRule rule, long count) {
        JSONObject object = (JSONObject) JSONObject.toJSON(rule);
        object.put("count", count);
        object.put("dataTime", System.currentTimeMillis());
        elasticLowerClient.insertListComm(Arrays.asList(object.toJSONString()),
        LogMessageConstant.LOG_MONITOR_MESSAGE_KEY);
    }

    private String getMonitorMessageURL(WarningRule rule) {
        //换算毫秒数
        int time = rule.getTime() * 1000;
        long currentTime = System.currentTimeMillis();
        //开始时间
        long startTime = currentTime - time;
        StringBuilder builder = new StringBuilder(64);
        builder.append(url).append("/#/?appName=").append(rule.getAppName())
                .append("&className=").append(rule.getClassName())
                .append("&logLevel=ERROR")
                .append("&time=").append(startTime).append(",").append(currentTime);
        return builder.toString();
    }
}
