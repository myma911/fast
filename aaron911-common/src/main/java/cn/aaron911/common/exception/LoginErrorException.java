package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

public class LoginErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.LOGIN_ERROR;
	

	public LoginErrorException() {
		super(stateCodeEnum);
	}

	public LoginErrorException(String message) {
		super(stateCodeEnum, message);
	}

	public LoginErrorException(String message, Throwable t) {
		super(stateCodeEnum, message, t);
	}
}

