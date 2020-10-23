package cn.aaron911.log.server.properties;


public class DefaultInitProperty{
    //最大每次发送日志条数
    public static int MAX_SEND_SIZE = 5000;
    //日志抓取频次间隔时间
    public static int MAX_INTERVAL = 100;
    //kafka消费组名称
    public static String KAFKA_GROUP_NAME = "logConsumer";

   /**
    * 模式类型
    *
    */
    public static class Mode{
    	public static String START = "redis&ui";
        public final static String REDIS_UI = "redis&ui";
    	public final static String KAFKA_UI = "kafka&ui";
    	public final static String UI = "ui";
    };

    

    public  static int ES_INDEX_SHARDS = 5;
    public  static int ES_INDEX_REPLICAS = 1;
    public  static String ES_REFRESH_INTERVAL = "30s";
    public  static String ES_INDEX_MODEL = "day";

    public static String restUserName = "";
    public static String restPassWord = "";
    public static String restUrl = "";


    public static String loginUsername = "";
    public static String loginPassword = "";
}
