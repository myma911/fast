package cn.aaron911.spider.exception;

import cn.aaron911.spider.consts.SpiderConsts;

/**
 * @version 1.0
 */
public class SpiderException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SpiderException(String message) {
        super(SpiderConsts.LOG_PREFIX + message);
    }

    public SpiderException(String message, Throwable cause) {
        super(SpiderConsts.LOG_PREFIX + message, cause);
    }
}
