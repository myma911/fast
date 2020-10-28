package cn.aaron911.idempotent.exception;

/**
 * aaron911-idempotent-spring-boot-starter 状态码类
 * 
 * @ClassName: StateCodeEnum
 * @author Aaron
 * @date 2020年8月31日 上午10:00:08
 */
public enum StateCodeEnum {
    SUCCESS        (911000, "成功"),
    EMPTY		   (911001, "请求头或请求参数中没有携带幂等性校验码"),
    INVALID        (911002, "幂等性校验码校验失败"),
    CREATE_FAILED  (911003, "幂等性校验码生成失败"),

    
    
    
    
    SYSTEM_ERROR   (911999, "系统错误");
    
   
   
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
