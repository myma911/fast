package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

public class RedisException extends BaseException {

	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.REDIS_ERROR;

	public RedisException() {
		super(stateCodeEnum);
	}
}

