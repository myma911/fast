package cn.aaron911.encrypt.api.exception;

/**
 * <p>加密方式未找到或未定义异常</p>
 */
public class EncryptMethodNotFoundException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.EncryptMethodNotFound;

    public EncryptMethodNotFoundException() {
        super(stateCodeEnum);
    }
}
