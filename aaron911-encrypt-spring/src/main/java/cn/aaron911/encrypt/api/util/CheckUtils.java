package cn.aaron911.encrypt.api.util;

import cn.aaron911.encrypt.api.exception.KeyNotConfiguredException;
import cn.hutool.core.util.StrUtil;

/**
 * <p>辅助检测工具类</p>
 * 
 */
public class CheckUtils {

    public static String checkAndGetKey(String k1,String k2,String keyName){
    	if (StrUtil.isBlank(k1) && StrUtil.isBlank(k2)) {
    	    throw new KeyNotConfiguredException(String.format("%s is not configured (未配置%s)", keyName,keyName));	
    	}
        if(k1==null) return k2;
        return k1;
    }

}
