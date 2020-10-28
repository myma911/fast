package cn.aaron911.common.result;

import cn.aaron911.common.exception.enums.StateCodeEnum;

/**
 * 返回结果实体类
 */
public class Result<T> {

	private Integer code;
    private String  traceId;
    private String message;
    private T data;

    public Result() {}


	public String getTraceId() {
		return traceId;
	}

	public Result (Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public Result(StateCodeEnum stateCodeEnum) {
    	this.code = stateCodeEnum.getCode();
        this.message = stateCodeEnum.getMessage();
    }
    
    
    
    /*********************************************************************/
    public static Result<java.lang.String> ok() {
    	Result<String> result = new Result<>();
    	result.setCode(StateCodeEnum.OK.getCode());
    	result.setMessage(StateCodeEnum.OK.getMessage());
    	return result;
    }
    
    public static <T> Result<T> ok(String message, T data) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.OK.getCode());
    	result.setMessage(message);
    	result.setData(data);
    	return result;
    }
    
    public static <T> Result<T> ok(T data) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.OK.getCode());
    	result.setData(data);
    	return result;
    }
    
    public static <T> Result<T> ok(String message) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.OK.getCode());
    	result.setMessage(message);
    	return result;
    }
    /*********************************************************************/
    
    
    
    /*********************************************************************/
    public static  Result<String> failed() {
    	Result<String> result = new Result<>();
    	result.setCode(StateCodeEnum.FAILED.getCode());
    	result.setMessage(StateCodeEnum.FAILED.getMessage());
    	return result;
    }
    
    public static <T> Result<T> failed(String message, T data) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.FAILED.getCode());
    	result.setMessage(message);
    	result.setData(data);
    	return result;
    }
    
    public static <T> Result<T> failed(T data) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.FAILED.getCode());
    	result.setData(data);
    	return result;
    }
    
    public static <T> Result<T> failed(String message) {
    	Result<T> result = new Result<>();
    	result.setCode(StateCodeEnum.FAILED.getCode());
    	result.setMessage(message);
    	return result;
    }
    /*********************************************************************/


	public Integer getCode() {
		return code;
	}


	public void setCode(Integer code) {
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public T getData() {
		return data;
	}


	public void setData(T data) {
		this.data = data;
	}


	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
}
