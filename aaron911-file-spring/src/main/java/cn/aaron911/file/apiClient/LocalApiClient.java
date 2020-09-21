package cn.aaron911.file.apiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import cn.aaron911.file.BaseApiClient;
import cn.aaron911.file.IProgressListener;
import cn.aaron911.file.entity.VirtualFile;
import cn.aaron911.file.exception.LocalApiException;
import cn.aaron911.file.util.FileUtil;
import cn.aaron911.file.util.StreamUtil;

/**
 * @version 1.0
 * 带监听器进度， 还没实现
 */
public class LocalApiClient extends BaseApiClient {
    private static final String DEFAULT_PREFIX = "fileupload/";

    private String url;
    private String rootPath;
    private String pathPrefix;
    private IProgressListener listener;
    
    public LocalApiClient() {
        super("Nginx文件服务器");
    }

    public LocalApiClient(IProgressListener listener) {
        super("Nginx文件服务器", listener);
        this.listener = listener;
    }

    public LocalApiClient init(String url, String rootPath, String uploadType) {
        this.url = url;
        this.rootPath = rootPath;

        this.pathPrefix = StringUtils.isEmpty(uploadType) ? DEFAULT_PREFIX : uploadType.endsWith("/") ? uploadType : uploadType + "/";
        
        return this;
    }
    
    /**
     * 不带监听器
     */
    @Override
    public VirtualFile uploadFile(InputStream is, String imageUrl) {
        this.check();

        String key = FileUtil.generateTempFileName(imageUrl);
        this.createNewFileName(key, this.pathPrefix);
        Date startTime = new Date();

        String realFilePath = this.rootPath + this.newFileName;
        FileUtil.checkFilePath(realFilePath);
        try (InputStream uploadIs = StreamUtil.clone(is);
             InputStream fileHashIs = StreamUtil.clone(is);
             FileOutputStream fos = new FileOutputStream(realFilePath)) {
             FileCopyUtils.copy(uploadIs, fos);
            return new VirtualFile()
                    .setOriginalFileName(FileUtil.getName(key))
                    .setSuffix(this.suffix)
                    .setUploadStartTime(startTime)
                    .setUploadEndTime(new Date())
                    .setFilePath(this.newFileName)
                    .setFileHash(DigestUtils.md5DigestAsHex(fileHashIs))
                    .setFullFilePath(this.url + this.newFileName);
        } catch (Exception e) {
            throw new LocalApiException("[" + this.storageType + "]文件上传失败：" + e.getMessage() + imageUrl);
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
     * 带监听器
     */
    @Override
    public VirtualFile uploadFile(InputStream is, String imageUrl, IProgressListener listener) {
        this.check();

        String key = FileUtil.generateTempFileName(imageUrl);
        this.createNewFileName(key, this.pathPrefix);
        Date startTime = new Date();

        String realFilePath = this.rootPath + this.newFileName;
        FileUtil.checkFilePath(realFilePath);
        try (InputStream uploadIs = StreamUtil.clone(is);
             InputStream fileHashIs = StreamUtil.clone(is);
             FileOutputStream fos = new FileOutputStream(realFilePath)) {
             FileCopyUtils.copy(uploadIs, fos);
            return new VirtualFile()
                    .setOriginalFileName(FileUtil.getName(key))
                    .setSuffix(this.suffix)
                    .setUploadStartTime(startTime)
                    .setUploadEndTime(new Date())
                    .setFilePath(this.newFileName)
                    .setFileHash(DigestUtils.md5DigestAsHex(fileHashIs))
                    .setFullFilePath(this.url + this.newFileName);
        } catch (Exception e) {
            throw new LocalApiException("[" + this.storageType + "]文件上传失败：" + e.getMessage() + imageUrl);
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
            throw new LocalApiException("[" + this.storageType + "]删除文件失败：文件key为空");
        }
        File file = new File(this.rootPath + key);
        if (!file.exists()) {
            throw new LocalApiException("[" + this.storageType + "]删除文件失败：文件不存在[" + this.rootPath + key + "]");
        }
        try {
            return file.delete();
        } catch (Exception e) {
            throw new LocalApiException("[" + this.storageType + "]删除文件失败：" + e.getMessage());
        }
    }

    @Override
    public void check() {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(rootPath)) {
            throw new LocalApiException("[" + this.storageType + "]尚未配置Nginx文件服务器，文件上传功能暂时不可用！");
        }
    }
}
