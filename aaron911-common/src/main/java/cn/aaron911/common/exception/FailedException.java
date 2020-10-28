package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

/**
 * 失败
 */
public class FailedException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.FAILED;

	public FailedException() {
		super(stateCodeEnum);
	}

	public FailedException(String message) {
		super(stateCodeEnum, message);
	}

	public FailedException(String message, Throwable t) {
		super(stateCodeEnum, message, t);
	}

}

