package cn.aaron911.encrypt.api.exception;

/**
 *  状态码类
 * 
 */
public enum StateCodeEnum {
    SUCCESS        			(911000, "成功"),
    DecryptFailed		    (911001, "解密失败"),
    DecryptMethodNotFound   (911002, "解密方法未找到"),
    EncryptFailed  			(911003, "加密失败"),
    EncryptMethodNotFound   (911004, "加密方法未找到"),
    KeyNotConfigured   		(911005, "key未配置"),
    
    
    
    SYSTEM_ERROR   			(911999, "系统错误");
    
   
   
    private int code;
    private String message;

    StateCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     *
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(int code) {
        for (StateCodeEnum s : StateCodeEnum.values()) {
            if (s.code == code) {
                return s.message;
            }
        }
        return null;
    }
}
