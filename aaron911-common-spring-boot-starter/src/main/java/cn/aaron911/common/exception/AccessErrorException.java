package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

/**
 * 
 */
public class AccessErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.ACCESS_ERROR;
	
	public AccessErrorException() {
		super(stateCodeEnum);
	}
}

