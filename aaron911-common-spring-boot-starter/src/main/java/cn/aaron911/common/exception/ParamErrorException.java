package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

public class ParamErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.PARAM_ERROR;

	public ParamErrorException() {
		super(stateCodeEnum);
	}

	public ParamErrorException(String message) {
		super(stateCodeEnum, message);
	}

	public ParamErrorException(String message, Throwable t) {
		super(stateCodeEnum, message, t);
	}
}

