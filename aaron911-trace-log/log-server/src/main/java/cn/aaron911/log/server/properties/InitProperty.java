package cn.aaron911.log.server.properties;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InitProperty implements InitializingBean {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(InitProperty.class);
    

    @Value("${aaron911.log.model:redis&ui}")
    private String model;
    
    /**
     * 使用kafka,配置地址
     */
    @Value("${aaron911.log.kafka.kafkaHosts:}")
    private String kafkaHosts;
    
    /**
     * 使用elasticsearch,配置地址
     */
    @Value("${aaron911.log.es.esHosts:}")
    private String esHosts;
    
    @Value("${aaron911.log.es.userName:}")
    private String esUserName;
    
    @Value("${aaron911.log.es.passWord:}")
    private String esPassWord;
    
    @Value("${aaron911.log.es.shards:5}")
    private int shards;
    
    @Value("${aaron911.log.es.replicas:1}")
    private int replicas;
    
    @Value("${aaron911.log.es.refresh.interval:60s}")
    private String refreshInterval;
    
    /**
     * #日志索引建立方式day表示按天、hour表示按照小时
     */
    @Value("${aaron911.log.es.indexType.model:day}")
    private String indexTypeModel;
    
    @Value("${aaron911.log.redis.redisHost:127.0.0.1:6379}")
    private String redisHost;
    
    @Value("${aaron911.log.redis.redisPassWord:}")
    private String redisPassWord;
    
    @Value("${aaron911.log.redis.redisDb:0}")
    private int redisDb=0;
    
    /**
     * #单次拉取日志条数, 从redis 或者kafka 中拉取
     */
    @Value("${aaron911.log.maxSendSize:5000}")
    public int maxSendSize = 5000;
    
    /**
     * #拉取时间间隔，kafka不生效
     */
    @Value("${aaron911.log.interval:100}")
    public int interval = 100;
    
    @Value("${aaron911.log.kafka.kafkaGroupName:logConsumer}")
    public String kafkaGroupName = "logConsumer";
    
    /**
     * #登录配置
     */
    @Value("${login.username:}")
    private String loginUsername;
    
    /**
     * #登录配置
     */
    @Value("${login.password:}")
    private String loginPassword;


    /**
     * 加载配置
     */
    private void loadConfig() {
        DefaultInitProperty.MAX_SEND_SIZE = this.maxSendSize;
        DefaultInitProperty.KAFKA_GROUP_NAME = this.kafkaGroupName;
        DefaultInitProperty.MAX_INTERVAL = this.interval;
        DefaultInitProperty.Mode.START = this.model;

        DefaultInitProperty.ES_INDEX_SHARDS=this.shards;
        DefaultInitProperty.ES_INDEX_REPLICAS=this.replicas;
        DefaultInitProperty.ES_REFRESH_INTERVAL=this.refreshInterval;
        DefaultInitProperty.ES_INDEX_MODEL=this.indexTypeModel;

        DefaultInitProperty.loginUsername = this.loginUsername;
        DefaultInitProperty.loginPassword = this.loginPassword;

        logger.info("server run model:" + this.model);
        logger.info("maxSendSize:" + this.maxSendSize);
        logger.info("interval:" + this.interval);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadConfig();
    }

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getKafkaHosts() {
		return kafkaHosts;
	}

	public void setKafkaHosts(String kafkaHosts) {
		this.kafkaHosts = kafkaHosts;
	}

	public String getEsHosts() {
		return esHosts;
	}

	public void setEsHosts(String esHosts) {
		this.esHosts = esHosts;
	}

	public String getEsUserName() {
		return esUserName;
	}

	public void setEsUserName(String esUserName) {
		this.esUserName = esUserName;
	}

	public String getEsPassWord() {
		return esPassWord;
	}

	public void setEsPassWord(String esPassWord) {
		this.esPassWord = esPassWord;
	}

	public int getShards() {
		return shards;
	}

	public void setShards(int shards) {
		this.shards = shards;
	}

	public int getReplicas() {
		return replicas;
	}

	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}

	public String getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(String refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	public String getIndexTypeModel() {
		return indexTypeModel;
	}

	public void setIndexTypeModel(String indexTypeModel) {
		this.indexTypeModel = indexTypeModel;
	}

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	public String getRedisPassWord() {
		return redisPassWord;
	}

	public void setRedisPassWord(String redisPassWord) {
		this.redisPassWord = redisPassWord;
	}

	public int getRedisDb() {
		return redisDb;
	}

	public void setRedisDb(int redisDb) {
		this.redisDb = redisDb;
	}

	public int getMaxSendSize() {
		return maxSendSize;
	}

	public void setMaxSendSize(int maxSendSize) {
		this.maxSendSize = maxSendSize;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getKafkaGroupName() {
		return kafkaGroupName;
	}

	public void setKafkaGroupName(String kafkaGroupName) {
		this.kafkaGroupName = kafkaGroupName;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
    
    
    
}
