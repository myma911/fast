package cn.aaron911.file.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileProperties {

    /**
     * 存储类型
     * StorageTypeEnum 中的一种
     */
	@Value("${aaron911.file.storageType: }")
	private String storageType = "";
	
	
	private StorageTypeEnum storageTypeEnum;
	
    /**
     * 文件服务器域名
     */
	@Value("${aaron911.file.localFileUrl: }")
	private String localFileUrl = "";
	
    /**
     * 文件存储路径
     */
	@Value("${aaron911.file.localFilePath: }")
	private String localFilePath = "";
	
/*****************************************************/	
	
    /**
     * 七牛云Bucket 名称
     */
	@Value("${aaron911.file.qiniuBucketName: }")
	private String qiniuBucketName = "";
	
    /**
     * 七牛云AccessKey
     */
	@Value("${aaron911.file.qiniuAccessKey: }")
	private String qiniuAccessKey = "";
	
    /**
     * 七牛云Secret Key
     */
	@Value("${aaron911.file.qiniuSecretKey: }")
	private String qiniuSecretKey = "";
	
    /**
     * 七牛云cdn域名
     */
	@Value("${aaron911.file.qiniuBasePath: }")
	private String qiniuBasePath = "";
	
/*****************************************************/
	
    /**
     * 阿里云Bucket 名称
     */
	@Value("${aaron911.file.aliyunBucketName: }")
	private String aliyunBucketName = "";
	
    /**
     * 阿里云地域节点（EndPoint）
     */
	@Value("${aaron911.file.aliyunEndpoint: }")
	private String aliyunEndpoint = "";
	
    /**
     * 阿里云Bucket 域名
     */
	@Value("${aaron911.file.aliyunFileUrl: }")
	private String aliyunFileUrl = "";
	
    /**
     * 阿里云Access Key
     */
	@Value("${aaron911.file.aliyunAccessKey: }")
	private String aliyunAccessKey = "";
	
    /**
     * 阿里云Access Key Secret
     */
	@Value("${aaron911.file.aliyunAccessKeySecret: }")
	private String aliyunAccessKeySecret = "";
	
/*****************************************************/	
	
	/**
	 * MinIo 存储
	 */
	@Value("${aaron911.file.minioEndpoint: }")
    private String minioEndpoint = "";
    
	/**
	 * MinIo 存储 
	 */
	@Value("${aaron911.file.minioAccessKey: }")
	private String minioAccessKey = "";
	
	/**
	 * MinIo 存储
	 */
	@Value("${aaron911.file.minioSecretKey: }")
    private String minioSecretKey = "";

/*****************************************************/
	
    public String getMinioEndpoint() {
		return minioEndpoint;
	}
	public void setMinioEndpoint(String minioEndpoint) {
		this.minioEndpoint = minioEndpoint;
	}
	public String getMinioAccessKey() {
		return minioAccessKey;
	}
	public void setMinioAccessKey(String minioAccessKey) {
		this.minioAccessKey = minioAccessKey;
	}
	public String getMinioSecretKey() {
		return minioSecretKey;
	}
	public void setMinioSecretKey(String minioSecretKey) {
		this.minioSecretKey = minioSecretKey;
	}
	
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
		this.storageTypeEnum = StorageTypeEnum.valueOf(storageType);
	}
	public String getLocalFileUrl() {
		return localFileUrl;
	}
	public void setLocalFileUrl(String localFileUrl) {
		this.localFileUrl = localFileUrl;
	}
	public String getLocalFilePath() {
		return localFilePath;
	}
	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}
	public String getQiniuBucketName() {
		return qiniuBucketName;
	}
	public void setQiniuBucketName(String qiniuBucketName) {
		this.qiniuBucketName = qiniuBucketName;
	}
	public String getQiniuAccessKey() {
		return qiniuAccessKey;
	}
	public void setQiniuAccessKey(String qiniuAccessKey) {
		this.qiniuAccessKey = qiniuAccessKey;
	}
	public String getQiniuSecretKey() {
		return qiniuSecretKey;
	}
	public void setQiniuSecretKey(String qiniuSecretKey) {
		this.qiniuSecretKey = qiniuSecretKey;
	}
	public String getQiniuBasePath() {
		return qiniuBasePath;
	}
	public void setQiniuBasePath(String qiniuBasePath) {
		this.qiniuBasePath = qiniuBasePath;
	}
	public String getAliyunBucketName() {
		return aliyunBucketName;
	}
	public void setAliyunBucketName(String aliyunBucketName) {
		this.aliyunBucketName = aliyunBucketName;
	}
	public String getAliyunEndpoint() {
		return aliyunEndpoint;
	}
	public void setAliyunEndpoint(String aliyunEndpoint) {
		this.aliyunEndpoint = aliyunEndpoint;
	}
	public String getAliyunFileUrl() {
		return aliyunFileUrl;
	}
	public void setAliyunFileUrl(String aliyunFileUrl) {
		this.aliyunFileUrl = aliyunFileUrl;
	}
	public String getAliyunAccessKey() {
		return aliyunAccessKey;
	}
	public void setAliyunAccessKey(String aliyunAccessKey) {
		this.aliyunAccessKey = aliyunAccessKey;
	}
	public String getAliyunAccessKeySecret() {
		return aliyunAccessKeySecret;
	}
	public void setAliyunAccessKeySecret(String aliyunAccessKeySecret) {
		this.aliyunAccessKeySecret = aliyunAccessKeySecret;
	}
	public StorageTypeEnum getStorageTypeEnum() {
		return storageTypeEnum;
	}
}
