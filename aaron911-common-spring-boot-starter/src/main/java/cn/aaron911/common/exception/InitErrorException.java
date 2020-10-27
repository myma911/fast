package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

/**
 * 初始化错误
 */
public class InitErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.INIT_ERROR;

	public InitErrorException() {
		super(stateCodeEnum);
	}

	public InitErrorException(String message) {
		super(stateCodeEnum, message);
	}

	public InitErrorException(String message, Throwable t) {
		super(stateCodeEnum, message, t);
	}
}

