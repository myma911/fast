package cn.aaron911.encrypt.api.exception;

/**
 * <p>解密数据失败异常</p>
 */
public class DecryptBodyFailException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.DecryptFailed;

    public DecryptBodyFailException() {
        super(stateCodeEnum);
    }
}