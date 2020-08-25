package cn.aaron911.file.alioss.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import cn.aaron911.file.exception.MinIoApiException;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.RegionConflictException;
import io.minio.errors.XmlParserException;

/**
 * @version 1.0
 */
public class MinIoApi {

    private MinioClient client;

    public MinIoApi(MinioClient client) {
        this.client = client;
    }

    public MinIoApi(String endpoint, String accessKeyId, String accessKeySecret) {
        try {
			this.client = new MinioClient(endpoint, accessKeyId, accessKeySecret);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MinIoApiException("[MinIo]：" + e.getMessage());
		}
    }
    
    
    /**
     * 判断 bucket是否存在
     * @param bucketName
     * @return
     * @throws IOException 
     * @throws XmlParserException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidResponseException 
     * @throws InvalidBucketNameException 
     * @throws InternalException 
     * @throws InsufficientDataException 
     * @throws IllegalArgumentException 
     * @throws ErrorResponseException 
     * @throws InvalidKeyException 
     */
    public boolean bucketExists(String bucketName) throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException{
    	return client.bucketExists(bucketName);
    }

    /**
     * 创建 bucket
     * @param bucketName
     * @throws IOException 
     * @throws XmlParserException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidResponseException 
     * @throws InvalidBucketNameException 
     * @throws InternalException 
     * @throws InsufficientDataException 
     * @throws IllegalArgumentException 
     * @throws ErrorResponseException 
     * @throws InvalidKeyException 
     * @throws RegionConflictException 
     */
    public void makeBucket(String bucketName) throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException, RegionConflictException{
        boolean isExist = client.bucketExists(bucketName);
        if(!isExist) {
        	client.makeBucket(bucketName);
        }
    }
    
    
    /**
     * 判断文件是否存在
     *
     * @param bucketName Name of the bucket.
     * @param objectName Object name in the bucket.
     * 
     * 
     * @throws IOException 
     * @throws XmlParserException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidResponseException 
     * @throws InvalidBucketNameException 
     * @throws InternalException 
     * @throws InsufficientDataException 
     * @throws IllegalArgumentException 
     * @throws ErrorResponseException 
     * @throws InvalidKeyException 
     */
    public boolean isExistFile(String bucketName, String objectName) throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException {
        try {
            if (!this.client.bucketExists(bucketName)) {
                throw new MinIoApiException("[MinIO] Bucket不存在：" + bucketName);
            }
            return this.client.statObject(bucketName, objectName) != null;
            		
        } finally {
            this.shutdown();
        }
    }
    
    
    
    

    /**
     * 文件上传
     * @param bucketName
     * @param objectName
     * @param filename
     * @throws IOException 
     * @throws XmlParserException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidResponseException 
     * @throws InvalidBucketNameException 
     * @throws InternalException 
     * @throws InsufficientDataException 
     * @throws IllegalArgumentException 
     * @throws ErrorResponseException 
     * @throws InvalidKeyException 
     */
    public void putObject(String bucketName, String objectName, String filename) throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException{
    	client.putObject(bucketName,objectName,filename,null);
        
    }
    /**
     * 文件上传
     * @param bucketName
     * @param objectName
     * @param stream
     * @throws IOException 
     * @throws XmlParserException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidResponseException 
     * @throws InvalidBucketNameException 
     * @throws InternalException 
     * @throws InsufficientDataException 
     * @throws IllegalArgumentException 
     * @throws ErrorResponseException 
     * @throws InvalidKeyException 
     */
    public void putObject(String bucketName, String objectName, InputStream stream) throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException{
    	client.putObject(bucketName,objectName,stream, new PutObjectOptions(stream.available(), -1));
    }
    
    
    
    
    public void putObject(String bucketName, String objectName, File localFile) throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException{
    	InputStream inputStream = new FileInputStream(localFile);
    	client.putObject(bucketName,objectName,inputStream, new PutObjectOptions(inputStream.available(), -1));
    }
    
    
    
    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     * @throws IOException 
     * @throws XmlParserException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidResponseException 
     * @throws InvalidBucketNameException 
     * @throws InternalException 
     * @throws InsufficientDataException 
     * @throws IllegalArgumentException 
     * @throws ErrorResponseException 
     * @throws InvalidKeyException 
     */
    public void removeObject(String bucketName, String objectName) throws InvalidKeyException, ErrorResponseException, IllegalArgumentException, InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IOException{
       
    	client.removeObject(bucketName,objectName);
       
    }
    
    
    private void shutdown() {
        //this.client
    }
}
