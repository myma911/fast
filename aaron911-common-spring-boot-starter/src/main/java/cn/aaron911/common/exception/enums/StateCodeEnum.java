package cn.aaron911.common.exception.enums;

/**
 * 状态码
 * 
 */
public enum StateCodeEnum {
   
	OK        	    			    (911000, "成功"),
    FAILED 		    			    (911001, "失败"),
    ERROR						    (911002, "错误"),
    IDEMPOTENT_EMPTY		        (911003, "请求头或请求参数中没有携带幂等性校验码"),
    IDEMPOTENT_INVALID              (911004, "幂等性校验码校验失败"),
    IDEMPOTENT_CREATE_FAILED        (911005, "幂等性校验码生成失败"),
    ACCESS_ERROR				    (911006, "权限不足"),
    REMOTE_ERROR				    (911007, "远程调用失败"),
    REPEAT_ERROR				    (911008, "重复操作"),
    LOGIN_ERROR					    (911009, "用户名或密码错误"),
    TOKEN_EMPTY					    (911010, "请求头或请求参数中没有携带token"),
    TOKEN_EXPIRED_ERROR			    (911011, "token超时错误"),
    TOKEN_INVALID				    (911012, "token校验失败"),
    REDIS_ERROR				        (911013, "Redis错误"),
    CONSTRAINT_VIOLATION_ERROR      (911014, "参数（方法）校验错误"),
    METHOD_ARGUMENT_NOT_VALID_ERROR (911015, "参数（对象）校验错误"),
    BIND_ERROR                      (911016, "参数绑定错误（大多数因为参数没有传对）"),
    TYPE_MISMATCH_ERROR             (911017, "参数转换错误（大多数因为参数没有传对）"),
    PARAM_ERROR                     (911018, "参数错误"),
    INIT_ERROR				        (911101, "初始化错误"),
    IM_PUSH_ERROR                   (911102, "IM 消息发送错误"),
    SYSTEM_ERROR    			    (911999, "严重系统错误");
    
   
    private int code;
    private String message;

    StateCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public StateCodeEnum setMessage(String message){
        this.message = message;
        return this;
    }
}
