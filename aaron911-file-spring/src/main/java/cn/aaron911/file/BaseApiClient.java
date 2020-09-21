package cn.aaron911.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import cn.aaron911.file.entity.VirtualFile;
import cn.aaron911.file.exception.GlobalFileException;
import cn.aaron911.file.exception.OssApiException;
import cn.aaron911.file.exception.QiniuApiException;
import cn.aaron911.file.util.FileUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;

/**
 * @version 1.0
 */
public abstract class BaseApiClient implements ApiClient {

    protected String storageType;
    protected String newFileName;
    protected String suffix;
    protected IProgressListener listener;
    
    /**
     * 不带监听器进度
     */
    public BaseApiClient(String storageType) {
        this.storageType = storageType; 
    }

    public BaseApiClient(String storageType, IProgressListener listener) {
        this.storageType = storageType;
        this.listener = listener; 
    }

    @Override
    public VirtualFile uploadFile(MultipartFile file) {
        this.check();
        if (file == null) {
            throw new OssApiException("[" + this.storageType + "]文件上传失败：文件不可为空");
        }
        try {
            VirtualFile res = this.uploadFile(file.getInputStream(), file.getOriginalFilename(), listener);
            return res.setSize(file.getSize()).setOriginalFileName(file.getOriginalFilename());
        } catch (IOException e) {
            throw new GlobalFileException("[" + this.storageType + "]文件上传失败：" + e.getMessage());
        }
    }

    @Override
    public VirtualFile uploadFile(File file) {
        this.check();
        if (file == null) {
            throw new QiniuApiException("[" + this.storageType + "]文件上传失败：文件不可为空");
        }
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            int available = is.available();
            VirtualFile res = this.uploadFile(is, "temp" + FileUtil.getSuffix(file), listener);
            return res.setSize(available).setOriginalFileName(file.getName());
        } catch (IOException e) {
            throw new GlobalFileException("[" + this.storageType + "]文件上传失败：" + e.getMessage());
        }
    }

    protected void createNewFileName(String key, String pathPrefix) {
        this.suffix = FileUtil.getSuffix(key);
        String fileName = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS") + "_" + IdUtil.fastSimpleUUID();
        this.newFileName = pathPrefix + (fileName + this.suffix);
    }

    protected abstract void check();
}
