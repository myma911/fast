package cn.aaron911.dynamic.datasource.exception;


/**
 * 幂等性校验码生成失败
 * @author Aaron
 * @date 2020年8月31日 下午4:44:40
 */
public class IdempotentCreateFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.CREATE_FAILED;
	
	private int code;
	
	private String msg;

	public IdempotentCreateFailedException() {
		super(stateCodeEnum.getMessage());
		this.msg = stateCodeEnum.getMessage();
		this.code = stateCodeEnum.getCode();
	}

	public StateCodeEnum getStateCodeEnum() {
		return stateCodeEnum;
	}


	public int getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}

}

