package cn.aaron911.file;

import org.springframework.beans.factory.annotation.Autowired;

import cn.aaron911.file.apiClient.AliyunOssApiClient;
import cn.aaron911.file.apiClient.LocalApiClient;
import cn.aaron911.file.apiClient.MinIoApiClient;
import cn.aaron911.file.apiClient.QiniuApiClient;
import cn.aaron911.file.exception.GlobalFileException;
import cn.aaron911.file.property.FileProperties;
import cn.aaron911.file.property.StorageTypeEnum;

/**
 * @version 1.0
 */
public class BaseFileUploader {

	@Autowired
	FileProperties fileProperties;
	
	/**
	 * 不带监听器进度
	 */
	ApiClient getApiClient(String uploadType) {

		StorageTypeEnum storageTypeEnum = fileProperties.getStorageTypeEnum();
		if (null == storageTypeEnum) {
			throw new GlobalFileException("[文件服务]当前系统暂未配置文件服务相关的内容！");
		}

		ApiClient res = null;
		switch (storageTypeEnum) {
		case local:
			String localFileUrl = fileProperties.getLocalFileUrl();
			String localFilePath = fileProperties.getLocalFilePath();
			res = new LocalApiClient().init(localFileUrl, localFilePath, uploadType);
			break;
		case qiniu:
			String accessKey = fileProperties.getQiniuAccessKey();
			String secretKey = fileProperties.getQiniuSecretKey();
			String qiniuBucketName = fileProperties.getQiniuBucketName();
			String baseUrl = fileProperties.getQiniuBasePath();
			res = new QiniuApiClient().init(accessKey, secretKey, qiniuBucketName, baseUrl, uploadType);
			break;
		case aliyun:
			String endpoint = fileProperties.getAliyunEndpoint();
			String accessKeyId = fileProperties.getAliyunAccessKey();
			String accessKeySecret = fileProperties.getAliyunAccessKeySecret();
			String url = fileProperties.getAliyunFileUrl();
			String aliYunBucketName = fileProperties.getAliyunBucketName();
			res = new AliyunOssApiClient().init(endpoint, accessKeyId, accessKeySecret, url, aliYunBucketName, uploadType);
			break;
		case minio:
			String minioEndpoint = fileProperties.getMinioEndpoint();
			String minioAccessKey = fileProperties.getMinioAccessKey();
			String minioSecretKey = fileProperties.getMinioSecretKey();
			res = new MinIoApiClient().init(minioEndpoint, minioAccessKey, minioSecretKey, uploadType);

			break;

		default:
			break;
		}
		if (null == res) {
			throw new GlobalFileException("[文件服务]当前系统暂未配置文件服务相关的内容！");
		}
		return res;
	}
	

	ApiClient getApiClient(String uploadType, IProgressListener listener) {

		StorageTypeEnum storageTypeEnum = fileProperties.getStorageTypeEnum();
		if (null == storageTypeEnum) {
			throw new GlobalFileException("[文件服务]当前系统暂未配置文件服务相关的内容！");
		}

		ApiClient res = null;
		switch (storageTypeEnum) {
		case local:
			String localFileUrl = fileProperties.getLocalFileUrl();
			String localFilePath = fileProperties.getLocalFilePath();
			res = new LocalApiClient(listener).init(localFileUrl, localFilePath, uploadType);
			break;
		case qiniu:
			String accessKey = fileProperties.getQiniuAccessKey();
			String secretKey = fileProperties.getQiniuSecretKey();
			String qiniuBucketName = fileProperties.getQiniuBucketName();
			String baseUrl = fileProperties.getQiniuBasePath();
			res = new QiniuApiClient().init(accessKey, secretKey, qiniuBucketName, baseUrl, uploadType);
			break;
		case aliyun:
			String endpoint = fileProperties.getAliyunEndpoint();
			String accessKeyId = fileProperties.getAliyunAccessKey();
			String accessKeySecret = fileProperties.getAliyunAccessKeySecret();
			String url = fileProperties.getAliyunFileUrl();
			String aliYunBucketName = fileProperties.getAliyunBucketName();
			res = new AliyunOssApiClient(listener).init(endpoint, accessKeyId, accessKeySecret, url, aliYunBucketName, uploadType);
			break;
		case minio:
			String minioEndpoint = fileProperties.getMinioEndpoint();
			String minioAccessKey = fileProperties.getMinioAccessKey();
			String minioSecretKey = fileProperties.getMinioSecretKey();
			res = new MinIoApiClient().init(minioEndpoint, minioAccessKey, minioSecretKey, uploadType);

			break;

		default:
			break;
		}
		if (null == res) {
			throw new GlobalFileException("[文件服务]当前系统暂未配置文件服务相关的内容！");
		}
		return res;
	}

}
