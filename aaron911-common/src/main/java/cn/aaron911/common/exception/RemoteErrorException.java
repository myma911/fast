package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

/**
 * 远程调用失败
 */
public class RemoteErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.REMOTE_ERROR;

	public RemoteErrorException() {
		super(stateCodeEnum);
	}

	public RemoteErrorException(String message) {
		super(stateCodeEnum, message);
	}

	public RemoteErrorException(String message, Throwable t) {
		super(stateCodeEnum, message, t);
	}
}

