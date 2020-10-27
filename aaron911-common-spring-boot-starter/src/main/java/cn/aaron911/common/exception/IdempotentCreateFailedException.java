package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

/**
 * 幂等性校验码生成失败
 * 
 */
public class IdempotentCreateFailedException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.IDEMPOTENT_CREATE_FAILED;
	
	public IdempotentCreateFailedException() {
		super(stateCodeEnum);
	}

}

