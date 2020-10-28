package cn.aaron911.encrypt.api.exception;

/**
 * <p>加密方式未找到或未定义异常</p>
 * 
 */
public class DecryptMethodNotFoundException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.DecryptMethodNotFound;

    public DecryptMethodNotFoundException() {
        super(stateCodeEnum);
    }
}
