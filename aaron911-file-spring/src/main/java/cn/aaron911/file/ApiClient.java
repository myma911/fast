package cn.aaron911.file;

import org.springframework.web.multipart.MultipartFile;

import cn.aaron911.file.entity.VirtualFile;

import java.io.File;
import java.io.InputStream;

/**
 * @version 1.0
 */
public interface ApiClient {

    VirtualFile uploadImg(MultipartFile file);

    VirtualFile uploadImg(File file);

    VirtualFile uploadImg(InputStream is, String key);

    boolean removeFile(String key);
}
