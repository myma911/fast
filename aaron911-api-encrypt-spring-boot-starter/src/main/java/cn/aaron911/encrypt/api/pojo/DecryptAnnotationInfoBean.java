package cn.aaron911.encrypt.api.pojo;

import cn.aaron911.encrypt.api.enums.DecryptBodyMethod;


/**
 * <p>解密注解信息</p>
 * 
 */
public class DecryptAnnotationInfoBean {

    private DecryptBodyMethod decryptBodyMethod;

    private String key;

	public DecryptBodyMethod getDecryptBodyMethod() {
		return decryptBodyMethod;
	}

	public void setDecryptBodyMethod(DecryptBodyMethod decryptBodyMethod) {
		this.decryptBodyMethod = decryptBodyMethod;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public DecryptAnnotationInfoBean(DecryptBodyMethod decryptBodyMethod, String key) {
		super();
		this.decryptBodyMethod = decryptBodyMethod;
		this.key = key;
	}
    
    

}
