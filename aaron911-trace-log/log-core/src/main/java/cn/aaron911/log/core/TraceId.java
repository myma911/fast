package cn.aaron911.log.core;

import com.alibaba.ttl.TransmittableThreadLocal;
import cn.hutool.core.util.IdUtil;


public class TraceId {
    public static TransmittableThreadLocal<String> logTraceID = new TransmittableThreadLocal<String>();

    public static void set() {
        String traceid= IdUtil.fastSimpleUUID();
        logTraceID.set(traceid);
    }
}
