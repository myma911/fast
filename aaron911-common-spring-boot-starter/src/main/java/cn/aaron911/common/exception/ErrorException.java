package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

/**
 * ErrorException
 */
public class ErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.ERROR;
	
	public ErrorException() {
		super(stateCodeEnum);
	}

	public ErrorException(String message) {
		super(stateCodeEnum, message);
	}

	public ErrorException(String message, Throwable t) {
		super(stateCodeEnum, message, t);
	}

}

