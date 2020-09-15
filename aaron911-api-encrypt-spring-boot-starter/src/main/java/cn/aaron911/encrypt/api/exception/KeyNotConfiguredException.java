package cn.aaron911.encrypt.api.exception;

/**
 * <p>未配置KEY运行时异常</p>
 * 
 */

public class KeyNotConfiguredException extends BaseException {
	private static final long serialVersionUID = 1L;
	private static StateCodeEnum stateCodeEnum = StateCodeEnum.KeyNotConfigured;

    public KeyNotConfiguredException() {
        super(stateCodeEnum);
    }
    
    public KeyNotConfiguredException(String message) {
    	super(stateCodeEnum, message);
    }
}
