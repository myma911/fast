package cn.aaron911.encrypt.api.exception;

/**
 * <p>加密数据失败异常</p>
 * 
 */
public class EncryptBodyFailException  extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.EncryptFailed;

    public EncryptBodyFailException() {
        super(stateCodeEnum);
    }
}