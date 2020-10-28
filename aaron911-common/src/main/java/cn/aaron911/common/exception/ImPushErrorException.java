package cn.aaron911.common.exception;

import cn.aaron911.common.exception.enums.StateCodeEnum;

/**
 * IM 消息发送错误
 */
public class ImPushErrorException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.IM_PUSH_ERROR;

	public ImPushErrorException() {
		super(stateCodeEnum);
	}
}

