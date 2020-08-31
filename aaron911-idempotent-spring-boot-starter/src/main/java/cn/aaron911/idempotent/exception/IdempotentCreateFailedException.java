package cn.aaron911.idempotent.exception;


/**
 * 幂等性校验码生成失败
 * @author Aaron
 * @date 2020年8月31日 下午4:44:40
 */
public class IdempotentCreateFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private StateCodeEnum stateCodeEnum;
	
	private int code;
	
	private String msg;

	public IdempotentCreateFailedException() {
		stateCodeEnum = StateCodeEnum.CREATE_FAILED;
		this.code = stateCodeEnum.getCode();
		this.msg = stateCodeEnum.getMessage();
	}

	public StateCodeEnum getStateCodeEnum() {
		return stateCodeEnum;
	}

	public void setStateCodeEnum(StateCodeEnum stateCodeEnum) {
		this.stateCodeEnum = stateCodeEnum;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

