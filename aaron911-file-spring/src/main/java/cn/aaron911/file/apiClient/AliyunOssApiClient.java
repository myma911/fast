package cn.aaron911.file.apiClient;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import cn.aaron911.file.BaseApiClient;
import cn.aaron911.file.IProgressListener;
import cn.aaron911.file.alioss.api.OssApi;
import cn.aaron911.file.entity.VirtualFile;
import cn.aaron911.file.exception.OssApiException;
import cn.aaron911.file.util.FileUtil;
import cn.aaron911.file.util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @version 1.0
 */
public class AliyunOssApiClient extends BaseApiClient {
    private static final String DEFAULT_PREFIX = "fileupload/";
    private OssApi ossApi;
    private String url;
    private String bucketName;
    private String pathPrefix;
    private IProgressListener listener;
    
    /**
     * 不带进度监听器
     */
    public AliyunOssApiClient() {
        super("阿里云OSS");
    }

    
    /**
     * 带进度监听器
     */
    public AliyunOssApiClient(IProgressListener listener) {
        super("阿里云OSS", listener);
        this.listener = listener;
    }

    public AliyunOssApiClient init(String endpoint, String accessKeyId, String accessKeySecret, String url, String bucketName, String uploadType) {
        ossApi = new OssApi(endpoint, accessKeyId, accessKeySecret);
        this.url = url;
        this.bucketName = bucketName;

        this.pathPrefix = StringUtils.isEmpty(uploadType) ? DEFAULT_PREFIX : uploadType.endsWith("/") ? uploadType : uploadType + "/";
        return this;
    }
    
    /**
     * 不带进度监听器
     */
    @Override
    public VirtualFile uploadFile(InputStream is, String imageUrl) {
        this.check();

        String key = FileUtil.generateTempFileName(imageUrl);
        this.createNewFileName(key, this.pathPrefix);
        Date startTime = new Date();
        try (InputStream uploadIs = StreamUtil.clone(is);
             InputStream fileHashIs = StreamUtil.clone(is)) {
            ossApi.uploadFile(uploadIs, this.newFileName, bucketName);
            return new VirtualFile()
                    .setOriginalFileName(FileUtil.getName(key))
                    .setSuffix(this.suffix)
                    .setUploadStartTime(startTime)
                    .setUploadEndTime(new Date())
                    .setFilePath(this.newFileName)
                    .setFileHash(DigestUtils.md5DigestAsHex(fileHashIs))
                    .setFullFilePath(this.url + this.newFileName);
        } catch (IOException e) {
            throw new OssApiException("[" + this.storageType + "]文件上传失败：" + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 带进度监听器
     */
    @Override
    public VirtualFile uploadFile(InputStream is, String imageUrl, IProgressListener listener) {
        this.check();

        String key = FileUtil.generateTempFileName(imageUrl);
        this.createNewFileName(key, this.pathPrefix);
        Date startTime = new Date();
        try (InputStream uploadIs = StreamUtil.clone(is);
             InputStream fileHashIs = StreamUtil.clone(is)) {
            ossApi.uploadFile(uploadIs, this.newFileName, bucketName, listener);
            return new VirtualFile()
                    .setOriginalFileName(FileUtil.getName(key))
                    .setSuffix(this.suffix)
                    .setUploadStartTime(startTime)
                    .setUploadEndTime(new Date())
                    .setFilePath(this.newFileName)
                    .setFileHash(DigestUtils.md5DigestAsHex(fileHashIs))
                    .setFullFilePath(this.url + this.newFileName);
        } catch (IOException e) {
            throw new OssApiException("[" + this.storageType + "]文件上传失败：" + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean removeFile(String key) {
        this.check();

        if (StringUtils.isEmpty(key)) {
            throw new OssApiException("[" + this.storageType + "]删除文件失败：文件key为空");
        }

        try {
            this.ossApi.deleteFile(key, bucketName);
            return true;
        } catch (Exception e) {
            throw new OssApiException(e.getMessage());
        }
    }

    @Override
    public void check() {
        if (null == ossApi) {
            throw new OssApiException("[" + this.storageType + "]尚未配置阿里云OSS，文件上传功能暂时不可用！");
        }
    }
}
