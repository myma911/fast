package cn.aaron911.file;

import org.springframework.web.multipart.MultipartFile;

import cn.aaron911.file.entity.VirtualFile;

import java.io.File;
import java.io.InputStream;

/**
 * @version 1.0
 */
public interface ApiClient {

    VirtualFile uploadFile(MultipartFile file);

    VirtualFile uploadFile(File file);

    VirtualFile uploadFile(InputStream is, String key);
    
    VirtualFile uploadFile(InputStream is, String key, IProgressListener listener);

    boolean removeFile(String key);
}
