package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

public class TokenExpiredException extends BaseException {

	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.TOKEN_EXPIRED_ERROR;
	
	public TokenExpiredException() {
		super(stateCodeEnum);
	}
}

