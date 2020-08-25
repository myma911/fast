package cn.aaron911.minio.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import cn.aaron911.minio.property.MinIoProperties;
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
 * 工具类
 */
@Component
@Configuration
@EnableConfigurationProperties({MinIoProperties.class})
public class MinIoUtils {

    private MinIoProperties minIo;

    public MinIoUtils(MinIoProperties minIo) {
        this.minIo = minIo;
    }

    private MinioClient instance;

    @PostConstruct
    public void init() throws InvalidEndpointException, InvalidPortException {
        instance = new MinioClient(minIo.getEndpoint(),minIo.getAccessKey(),minIo.getSecretKey());
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
        
    	return instance.bucketExists(bucketName);
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

        boolean isExist = instance.bucketExists(bucketName);
        if(!isExist) {
            instance.makeBucket(bucketName);
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
       
        instance.putObject(bucketName,objectName,filename,null);
        
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
       
            instance.putObject(bucketName,objectName,stream, new PutObjectOptions(stream.available(), -1));
        
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
       
        instance.removeObject(bucketName,objectName);
       
    }
}