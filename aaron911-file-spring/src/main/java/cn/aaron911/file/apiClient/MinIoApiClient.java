package cn.aaron911.file.apiClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.util.DigestUtils;

import com.qiniu.util.StringUtils;

import cn.aaron911.file.BaseApiClient;
import cn.aaron911.file.IProgressListener;
import cn.aaron911.file.alioss.api.MinIoApi;
import cn.aaron911.file.entity.VirtualFile;
import cn.aaron911.file.exception.MinIoApiException;
import cn.aaron911.file.util.FileUtil;
import cn.aaron911.file.util.StreamUtil;

/**
 * MinIO
 *
 * @version 1.0
 */
public class MinIoApiClient extends BaseApiClient {

	private static final String DEFAULT_PREFIX = "fileupload/";

	private MinIoApi minIoApi;

	private String bucketName;
	private String url;
	private String pathPrefix;

	public MinIoApiClient() {
		super("MinIo存储");
	}

	public MinIoApiClient init(String endpoint, String accessKey, String secretKey, String uploadType) {
		minIoApi = new MinIoApi(endpoint, accessKey, secretKey);

		this.pathPrefix = StringUtils.isNullOrEmpty(uploadType) ? DEFAULT_PREFIX : uploadType.endsWith("/") ? uploadType : uploadType + "/";
		return this;
	}

	/**
	 * 上传
	 *
	 * @param is       流
	 * @param imageUrl 路径
	 * @return 上传后的路径
	 */
	@Override
	public VirtualFile uploadImg(InputStream is, String imageUrl) {
		this.check();

		String key = FileUtil.generateTempFileName(imageUrl);
		this.createNewFileName(key, this.pathPrefix);
		Date startTime = new Date();
		try (InputStream uploadIs = StreamUtil.clone(is); InputStream fileHashIs = StreamUtil.clone(is)) {
			minIoApi.putObject(bucketName, this.newFileName, uploadIs);
			return new VirtualFile().setOriginalFileName(FileUtil.getName(key)).setSuffix(this.suffix)
					.setUploadStartTime(startTime).setUploadEndTime(new Date()).setFilePath(this.newFileName)
					.setFileHash(DigestUtils.md5DigestAsHex(fileHashIs)).setFullFilePath(this.url + this.newFileName);
		} catch (Exception e) {
			throw new MinIoApiException("[" + this.storageType + "]文件上传失败：" + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
	}
	
	/**
	 * 带监听器， 还未实现
	 */
	@Override
	public VirtualFile uploadImg(InputStream is, String imageUrl, IProgressListener listener) {
		this.check();

		String key = FileUtil.generateTempFileName(imageUrl);
		this.createNewFileName(key, this.pathPrefix);
		Date startTime = new Date();
		try (InputStream uploadIs = StreamUtil.clone(is); InputStream fileHashIs = StreamUtil.clone(is)) {
			minIoApi.putObject(bucketName, this.newFileName, uploadIs);
			return new VirtualFile().setOriginalFileName(FileUtil.getName(key)).setSuffix(this.suffix)
					.setUploadStartTime(startTime).setUploadEndTime(new Date()).setFilePath(this.newFileName)
					.setFileHash(DigestUtils.md5DigestAsHex(fileHashIs)).setFullFilePath(this.url + this.newFileName);
		} catch (Exception e) {
			throw new MinIoApiException("[" + this.storageType + "]文件上传失败：" + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
	}

	/**
	 * 删除
	 *
	 * @param key 七牛空间中文件名称
	 */
	@Override
	public boolean removeFile(String key) {
		this.check();
		if (StringUtils.isNullOrEmpty(key)) {
			throw new MinIoApiException("[" + this.storageType + "]删除文件失败：文件key为空");
		}
		try {
			this.minIoApi.removeObject(bucketName, key);
            return true;
		} catch (Exception e) {
			throw new MinIoApiException("[" + this.storageType + "]删除文件发生异常：" + e.getMessage());
		}
	}

	@Override
	public void check() {
		if (null == minIoApi) {
			throw new MinIoApiException("[" + this.storageType + "]尚未配置MinIO，文件上传功能暂时不可用！");
		}
	}

	

//	public String getPath() {
//		return this.path;
//	}
}
