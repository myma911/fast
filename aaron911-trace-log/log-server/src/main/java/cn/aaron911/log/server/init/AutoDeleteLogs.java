package cn.aaron911.log.server.init;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.aaron911.log.server.properties.DefaultInitProperty;
import cn.aaron911.log.server.util.ElasticLowerClientSingleton;
import cn.aaron911.log.server.util.IndexUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

/**
 * className：AutoDeleteLogs
 * description：自动删除日志，凌晨0点执行
 */
@Component
public class AutoDeleteLogs {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(AutoDeleteLogs.class);
    
    @Autowired
    private ElasticLowerClientSingleton elasticLowerClient;
    
    @Value("${admin.log.keepDays:0}")
    private int keepDays;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteLogs() {
        if (keepDays > 0) {
            try {
                logger.info("begin delete {} days ago logs!", keepDays);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -keepDays);
                Date date = cal.getTime();
                String runLogIndex = LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_RUN + "_" + DateUtil.format(date, DatePattern.PURE_DATE_PATTERN);
                elasticLowerClient.deleteIndex(runLogIndex);
                String traceLogIndex = LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_TRACE + "_" + DateUtil.format(date, DatePattern.PURE_DATE_PATTERN);
                elasticLowerClient.deleteIndex(traceLogIndex);
                for (int a = 0; a < 24; a++) {
                    String hour=String.format("%02d",a);
                    elasticLowerClient.deleteIndex(runLogIndex+hour);
                    elasticLowerClient.deleteIndex(traceLogIndex+hour);
                }
            } catch (Exception e) {
                logger.error("delete logs error!", e);
            }
        } else {
            logger.info("unwanted delete logs");
        }
    }
    
    /**
     * 每天夜里11点自动创建第二天的索引
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void creatIndice() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date date = cal.getTime();
        if(DefaultInitProperty.ES_INDEX_MODEL.equals("day")){
            creatIndiceLog(IndexUtil.getRunLogIndex(date));
            creatIndiceTrace(IndexUtil.getTraceLogIndex(date));
        }else {
            for (int a = 0; a < 24; a++) {
                String hour=String.format("%02d",a);
                creatIndiceLog(IndexUtil.getRunLogIndex(date,hour));
                creatIndiceTrace(IndexUtil.getTraceLogIndex(date,hour));

            }
        }
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
}
