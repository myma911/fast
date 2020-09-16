package cn.aaron911.file;

import java.io.File;
import java.io.InputStream;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.aaron911.file.entity.VirtualFile;
import cn.aaron911.file.exception.GlobalFileException;

/**
 * @version 1.0
 */
public class GlobalFileUploader extends BaseFileUploader implements FileUploader {

	/**
	 * 不带监听器进度
	 */
	@Override
	public VirtualFile upload(InputStream is, String uploadType, String imageUrl, boolean save) {
		ApiClient apiClient = this.getApiClient(uploadType);
		VirtualFile virtualFile = apiClient.uploadImg(is, imageUrl);
		return virtualFile;
	}

	/**
	 * 不带监听器进度
	 */
	@Override
	public VirtualFile upload(File file, String uploadType, boolean save) {
		ApiClient apiClient = this.getApiClient(uploadType);
		VirtualFile virtualFile = apiClient.uploadImg(file);
		return virtualFile;
	}

	/**
	 * 不带监听器进度
	 */
	@Override
	public VirtualFile upload(MultipartFile file, String uploadType, boolean save) {
		ApiClient apiClient = this.getApiClient(uploadType);
		VirtualFile virtualFile = apiClient.uploadImg(file);
		return virtualFile;
	}



	/**
	 * 带监听器进度
	 */
	@Override
	public VirtualFile upload(InputStream is, String uploadType, String imageUrl, boolean save, IProgressListener listener) {
		ApiClient apiClient = this.getApiClient(uploadType, listener);
		VirtualFile virtualFile = apiClient.uploadImg(is, imageUrl, listener);
		return virtualFile;
	}

	/**
	 * 带监听器进度
	 */
	@Override
	public VirtualFile upload(File file, String uploadType, boolean save, IProgressListener listener) {
		ApiClient apiClient = this.getApiClient(uploadType, listener);
		VirtualFile virtualFile = apiClient.uploadImg(file);
		return virtualFile;
	}

	/**
	 * 带监听器进度
	 */
	@Override
	public VirtualFile upload(MultipartFile file, String uploadType, boolean save, IProgressListener listener) {
		ApiClient apiClient = this.getApiClient(uploadType, listener);
		VirtualFile virtualFile = apiClient.uploadImg(file);
		return virtualFile;
	}


	@Override
	public boolean delete(String filePath, String uploadType) {
		if (StringUtils.isEmpty(filePath)) {
			throw new GlobalFileException("[文件服务]文件删除失败，文件为空！");
		}

		ApiClient apiClient = this.getApiClient(uploadType);
		return apiClient.removeFile(filePath);
	}

}
