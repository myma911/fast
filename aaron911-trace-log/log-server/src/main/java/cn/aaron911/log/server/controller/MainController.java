package cn.aaron911.log.server.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.aaron911.log.core.LogMessage;
import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.core.dto.WarningRule;
import cn.aaron911.log.core.dto.WarningRuleDto;
import cn.aaron911.log.core.redis.RedisClient;
import cn.aaron911.log.server.cache.AppNameCache;
import cn.aaron911.log.server.controller.vo.LoginVO;
import cn.aaron911.log.server.properties.DefaultInitProperty;
import cn.aaron911.log.server.util.ElasticLowerClientSingleton;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;


@RestController
@CrossOrigin
public class MainController {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ElasticLowerClientSingleton elasticLowerClient;

    @Value("${admin.password}")
    private String adminPassWord;


    @RequestMapping({"/login"})
    public Result login(@RequestBody LoginVO login, HttpServletRequest request) {
        if(StringUtils.isEmpty(DefaultInitProperty.loginUsername)) {
            request.getSession().setAttribute("token", new Object());
            return new Result();
        }
        if(DefaultInitProperty.loginUsername.equals(login.getUsername()) && DefaultInitProperty.loginPassword.equals(login.getPassword())) {
            request.getSession().setAttribute("token", new Object());
            return new Result();
        } else {
            request.getSession().removeAttribute("token");
            return Result.INVALID_LOGIN;
        }
    }

    @RequestMapping({"/logout"})
    public void login(HttpServletRequest request) {
        request.getSession().removeAttribute("token");
    }

    @RequestMapping({"/getlog"})
    public Result getlog(Integer maxSendSize, String logKey) {
        if (maxSendSize == null) {
            maxSendSize = 500;
        }
        Result result = new Result();
        try {
            List<String> logs = redisClient.getMessage(logKey, maxSendSize);
            if (logs != null && logs.size() > 0) {
                logger.info("get logs success size:" + logs.size());
                result.setCode(200);
                result.setMessage("get logs success!");
                result.setLogs(logs);
                return result;
            }
        } catch (Exception e) {
            logger.error("", e);
            result.setCode(500);
            result.setMessage("get logs error! :" + e.getMessage());
        }
        result.setCode(404);
        result.setMessage("get no logs!");
        return result;
    }

    @RequestMapping({"/sendLog"})
    public Result sendLog(@RequestBody List<LogMessage> logs, String logKey) {
        Result result = new Result();
        if ("redis&ui".equals(DefaultInitProperty.Mode.START)) {
            try {
                List<String> logStr=new ArrayList<>();
                logs.forEach(log->{
                    logStr.add(JSONUtil.toJsonStr(log));
                });
                redisClient.putMessageList(logKey, logStr);
            } catch (Exception e) {
                result.setCode(500);
                result.setMessage("send logs error! :" + e.getMessage());
            }
            result.setCode(200);
            result.setMessage("send logs! success");
        } else {
            result.setCode(500);
            result.setMessage("send logs error! rest model only support redis model");
        }
        return result;
    }


    @RequestMapping({"/query"})
    public Map query(@RequestBody String queryStr, String index, String size, String from) {
        String indexStr = "";
        try {
            //检查ES索引是否存在
            String[] indexs = index.split(",");
            List<String> reindexs = elasticLowerClient.getExistIndices(indexs);
            indexStr = String.join(",", reindexs);
            if ("".equals(indexStr)) {
                return new JSONObject();
            }
            String url = "/" + indexStr + "/_search?from=" + from + "&size=" + size;
            logger.info("queryURL:" + url);
            logger.info("queryStr:" + queryStr);
            String result = elasticLowerClient.get(url, queryStr);
            ObjectMapper mapper = new ObjectMapper(); 
            Map readValue = mapper.readValue(result, Map.class);
            return readValue;
        } catch (Exception e) {
            logger.error("", e);
            return new JSONObject();
        }
    }

    @RequestMapping({"/getServerInfo"})
    public String query(String index) {
        String res = elasticLowerClient.cat(index);
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(res.getBytes(Charset.forName("utf8"))), Charset.forName("utf8")));
        List<String> list = new ArrayList<>();
        try {
            while (true) {
                String aa = br.readLine();
                if (StringUtils.isEmpty(aa)) {
                    break;
                }
                list.add(aa);
            }
            List<Map<String, String>> listMap = new ArrayList<>();
            if (list.size() > 0) {
                String[] title = list.get(0).split("\\s+");
                for (int i = 1; i < list.size(); i++) {
                    String[] values = list.get(i).split("\\s+");
                    Map<String, String> map = new HashMap<>();
                    for (int j = 0; j < title.length; j++) {
                        map.put(title[j], values[j]);
                    }
                    listMap.add(map);
                }
            }
            return JSONUtil.toJsonStr(listMap);
        } catch (IOException e) {
            logger.error("", e);
        }
        return "";
    }
    @RequestMapping({"/getQueueCounts"})
    public Map<String, Object> getQueueCounts() {
        Map<String, Object> map = new HashMap<>();
        map.put("runSize",redisClient.llen(LogMessageConstant.LOG_KEY));
        map.put("traceSize",redisClient.llen(LogMessageConstant.LOG_KEY_TRACE));
        return map;
    }

    @RequestMapping({"/deleteQueue"})
    public Map<String, Object> deleteQueue(String adminPassWord) {
        Map<String, Object> map = new HashMap<>();
        if (adminPassWord.equals(this.adminPassWord)) {
        redisClient.del(LogMessageConstant.LOG_KEY);
        redisClient.del(LogMessageConstant.LOG_KEY_TRACE);
        map.put("acknowledged", true);
        } else {
            map.put("acknowledged", false);
            map.put("message", "管理密码错误！");
        }
        return map;
    }

    @RequestMapping({"/deleteIndex"})
    public Map<String, Object> deleteIndex(String index, String adminPassWord) {
        Map<String, Object> map = new HashMap<>();
        if (adminPassWord.equals(this.adminPassWord)) {
            boolean re = elasticLowerClient.deleteIndex(index);
            if(index.startsWith(LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_RUN)){
                creatIndiceLog(index);
            }
            if(index.startsWith(LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_TRACE)){
                creatIndiceTrace(index);
            }
            map.put("acknowledged", re);
        } else {
            map.put("acknowledged", false);
            map.put("message", "管理密码错误！");
        }
        return map;
    }

    private void creatIndiceLog(String index){
        if(!elasticLowerClient.existIndice(index)){
            elasticLowerClient.creatIndice(index);
        };
    }
    private void creatIndiceTrace(String index){
        if(!elasticLowerClient.existIndice(index)){
            elasticLowerClient.creatIndiceTrace(index);
        };
    }
    @RequestMapping({"/getWarningRuleList"})
    public Object getWarningRuleList() {
        List<WarningRuleDto> list=new ArrayList<>();
        Map<String,String> map=redisClient.hgetAll(LogMessageConstant.WARN_RULE_KEY);
        for(Map.Entry<String, String> entry : map.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            WarningRule warningRule = JSONUtil.toBean(mapValue, WarningRule.class);
            WarningRuleDto warningRuleDto=new WarningRuleDto();
            warningRuleDto.setId(mapKey);
            warningRuleDto.setAppName(warningRule.getAppName());
            warningRuleDto.setClassName(warningRule.getClassName());
            warningRuleDto.setReceiver(warningRule.getReceiver());
            warningRuleDto.setWebhookUrl(warningRule.getWebhookUrl());
            warningRuleDto.setTime(warningRule.getTime());
            warningRuleDto.setErrorCount(warningRule.getErrorCount());
            warningRuleDto.setStatus(warningRule.getStatus());
            warningRuleDto.setHookServe(warningRule.getHookServe());
            list.add(warningRuleDto);
        }
        return list;
    }
    @RequestMapping({"/saveWarningRuleList"})
    public Object saveWarningRule(String id, @RequestBody WarningRule warningRule) {
    	String warningRuleStr = JSONUtil.toJsonStr(warningRule);
        redisClient.hset(LogMessageConstant.WARN_RULE_KEY,id,warningRuleStr);
        Map<String, Object> result = new HashMap<>();
        result.put("success",true);
        return result;
    }
    
    @RequestMapping({"/deleteWarningRule"})
    public Object deleteWarningRule(String id) {
        redisClient.hdel(LogMessageConstant.WARN_RULE_KEY,id);
        Map<String, Object> result = new HashMap<>();
        result.put("success",true);
        return result;
    }

    @RequestMapping({"/getExtendfieldList"})
    public Object getExtendfieldList(String appName) {
        Map<String,String> map=redisClient.hgetAll(LogMessageConstant.EXTEND_APP_MAP_KEY+appName);
        return map;
    }

    @RequestMapping({"/addExtendfield"})
    public Object addExtendfield(String appName,String field,String fieldName) {
        redisClient.hset(LogMessageConstant.EXTEND_APP_MAP_KEY+appName, field,fieldName);
        Map<String, Object> result = new HashMap<>();
        result.put("success",true);
        return result;
    }
    @RequestMapping({"/delExtendfield"})
    public Object delExtendfield(String appName,String field) {
        redisClient.hdel(LogMessageConstant.EXTEND_APP_MAP_KEY+appName, field);
        Map<String, Object> result = new HashMap<>();
        result.put("success",true);
        return result;
    }
    @RequestMapping({"/getAppNames"})
    public Object getAppNames() {
        return AppNameCache.appName;
    }
}
