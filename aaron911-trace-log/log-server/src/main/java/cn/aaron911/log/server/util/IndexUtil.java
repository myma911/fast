package cn.aaron911.log.server.util;

import java.util.Date;

import cn.aaron911.log.core.constant.LogMessageConstant;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

public class IndexUtil {

    public static String getRunLogIndex(Date date){
        return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_RUN + "_" + DateUtil.format(date, DatePattern.PURE_DATE_PATTERN);
    }
    public static String getTraceLogIndex(Date date){
        return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_TRACE + "_" + DateUtil.format(date, DatePattern.PURE_DATE_PATTERN);
    }
    public static String getRunLogIndex(Date date,String hour){
        return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_RUN + "_" + DateUtil.format(date, DatePattern.PURE_DATE_PATTERN) + hour;
    }
    public static String getTraceLogIndex(Date date,String hour){
        return LogMessageConstant.ES_INDEX + LogMessageConstant.LOG_TYPE_TRACE + "_" + DateUtil.format(date, DatePattern.PURE_DATE_PATTERN) + hour;
    }

}
