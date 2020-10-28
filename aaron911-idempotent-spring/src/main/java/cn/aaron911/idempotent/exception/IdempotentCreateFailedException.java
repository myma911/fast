package cn.aaron911.idempotent.exception;


/**
 * 幂等性校验码生成失败
 * @author Aaron
 * @date 2020年8月31日 下午4:44:40
 */
public class IdempotentCreateFailedException extends BaseException {
	private static final long serialVersionUID = 1L;
	
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.CREATE_FAILED;

	public IdempotentCreateFailedException() {
		super(stateCodeEnum);
	}
}

