package cn.aaron911.log.core.exception;

/**
 * 
 * description：连接日志队列异常抛出，redis挂了，或者kafk挂了
 * 
 */
public class LogQueueConnectException extends Exception{

    public LogQueueConnectException(){
        super();
    }
    public LogQueueConnectException(String message){
        super(message);
    }
    public LogQueueConnectException(String message, Throwable cause){
        super(message,cause);
    }
    public LogQueueConnectException(Throwable cause){
        super(cause);
    }
}
