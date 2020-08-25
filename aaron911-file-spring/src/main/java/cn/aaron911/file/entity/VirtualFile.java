package cn.aaron911.file.entity;

import java.util.Date;

/**
 * @version 1.0
 */
public class VirtualFile {
    /**
     * 文件大小
     */
    public Long size;
    /**
     * 文件后缀（Suffix）
     */
    public String suffix;
    /**
     * 图片文件的宽
     */
    public Integer width;
    /**
     * 图片文件的高
     */
    public Integer height;
    /**
     * 文件hash
     */
    private String fileHash;
    /**
     * 文件路径 （不带域名）
     */
    private String filePath;
    /**
     * 文件全路径 （带域名）
     */
    private String fullFilePath;
    /**
     * 原始文件名
     */
    private String originalFileName;
   
	/**
     * 文件上传开始的时间
     */
    private Date uploadStartTime;
    /**
     * 文件上传结束的时间
     */
    private Date uploadEndTime;

    public VirtualFile setFileHash(String fileHash) {
        this.fileHash = fileHash;
        return this;
    }

    public VirtualFile setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public VirtualFile setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
        return this;
    }

    public VirtualFile setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
        return this;
    }

    public VirtualFile setUploadStartTime(Date uploadStartTime) {
        this.uploadStartTime = uploadStartTime;
        return this;
    }

    public VirtualFile setUploadEndTime(Date uploadEndTime) {
        this.uploadEndTime = uploadEndTime;
        return this;
    }

    public long getUseTime() {
        Date uploadEndTime = this.getUploadEndTime();
        Date uploadStartTime = this.getUploadStartTime();
        if (null == uploadStartTime || null == uploadEndTime) {
            return -1;
        }
        return uploadEndTime.getTime() - uploadStartTime.getTime();
    }

    public VirtualFile setSize(long size) {
        this.size = size;
        return this;
    }

    public VirtualFile setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public VirtualFile setWidth(int width) {
        this.width = width;
        return this;
    }

    public VirtualFile setHeight(int height) {
        this.height = height;
        return this;
    }
    
    public Long getSize() {
		return size;
	}

	public VirtualFile setSize(Long size) {
		this.size = size;
		return this;
	}

	public Integer getWidth() {
		return width;
	}

	public VirtualFile setWidth(Integer width) {
		this.width = width;
		return this;
	}

	public Integer getHeight() {
		return height;
	}

	public VirtualFile setHeight(Integer height) {
		this.height = height;
		return this;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getFileHash() {
		return fileHash;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFullFilePath() {
		return fullFilePath;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public Date getUploadStartTime() {
		return uploadStartTime;
	}

	public Date getUploadEndTime() {
		return uploadEndTime;
	}

}
