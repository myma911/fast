package cn.aaron911.log.server.monitor;

import com.alibaba.fastjson.JSON;

import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.core.dto.WarningRule;
import cn.aaron911.log.core.redis.RedisClient;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class LogMonitorRuleConfig {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(LogMonitorRuleConfig.class);


    @Autowired
    private RedisClient redisClient;

    private static ConcurrentHashMap<String, List<WarningRule>> configMap = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, List<WarningRule>> backConfigMap = new ConcurrentHashMap<>();


    /**
     * @param appName
     * @return
     */
    public List<WarningRule> getMonitorRuleConfig(String appName) {
        if (configMap.isEmpty()) {
            initMonitorRuleConfig();
        }
        return configMap.get(appName);
    }

    public synchronized void initMonitorRuleConfig() {
        Map<String, String> configs = redisClient.hgetAll(LogMessageConstant.WARN_RULE_KEY);
        Collection<String> values = configs.values();
        Iterator<String> iterator = values.iterator();
        backConfigMap.clear();
        while (iterator.hasNext()) {
            parserConfig(iterator.next());
        }
        configMap = backConfigMap;
    }

    private static void parserConfig(String config) {
        WarningRule warningRule = JSON.parseObject(config, WarningRule.class);
        if (warningRule.getStatus() == 0) {
            return;
        }
        if (backConfigMap.containsKey(warningRule.getAppName())) {
            List<WarningRule> warningRules = backConfigMap.get(warningRule.getAppName());
            warningRules.add(warningRule);
            backConfigMap.put(warningRule.getAppName(), warningRules);
        } else {
            List<WarningRule> lists = new ArrayList<>();
            lists.add(warningRule);
            backConfigMap.put(warningRule.getAppName(), lists);
        }
    }


    @Scheduled(cron = "0 */1 * * * ?")
    private void configureTasks() {
        try {
            initMonitorRuleConfig();
        } catch (Exception e) {
            logger.error("更新规则配置失败 {}", e);

        }
    }
}
