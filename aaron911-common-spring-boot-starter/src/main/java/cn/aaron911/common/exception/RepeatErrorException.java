package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

public class RepeatErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.REPEAT_ERROR;
	
	public RepeatErrorException() {
		super(stateCodeEnum);
	}
}

