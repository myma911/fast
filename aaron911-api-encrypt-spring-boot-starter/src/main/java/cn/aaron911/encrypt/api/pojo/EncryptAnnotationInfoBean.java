package cn.aaron911.encrypt.api.pojo;

import cn.aaron911.encrypt.api.enums.EncryptBodyMethod;
import cn.aaron911.encrypt.api.enums.SHAEncryptType;

/**
 * <p>加密注解信息</p>
 * 
 */
public class EncryptAnnotationInfoBean {

    private EncryptBodyMethod encryptBodyMethod;

    private String key;

    private SHAEncryptType shaEncryptType;

	public EncryptBodyMethod getEncryptBodyMethod() {
		return encryptBodyMethod;
	}

	public void setEncryptBodyMethod(EncryptBodyMethod encryptBodyMethod) {
		this.encryptBodyMethod = encryptBodyMethod;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public SHAEncryptType getShaEncryptType() {
		return shaEncryptType;
	}

	public void setShaEncryptType(SHAEncryptType shaEncryptType) {
		this.shaEncryptType = shaEncryptType;
	}
	
	
	
	

	public EncryptAnnotationInfoBean(EncryptBodyMethod encryptBodyMethod, String key, SHAEncryptType shaEncryptType) {
		super();
		this.encryptBodyMethod = encryptBodyMethod;
		this.key = key;
		this.shaEncryptType = shaEncryptType;
	}

	public EncryptAnnotationInfoBean(EncryptBodyMethod encryptBodyMethod) {
		super();
		this.encryptBodyMethod = encryptBodyMethod;
	}

	public EncryptAnnotationInfoBean(EncryptBodyMethod encryptBodyMethod, SHAEncryptType shaEncryptType) {
		super();
		this.encryptBodyMethod = encryptBodyMethod;
		this.shaEncryptType = shaEncryptType;
	}

	public EncryptAnnotationInfoBean(EncryptBodyMethod encryptBodyMethod, String key) {
		super();
		this.encryptBodyMethod = encryptBodyMethod;
		this.key = key;
	}
}
