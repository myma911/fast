package cn.aaron911.file.exception;

/**
 * @version 1.0
 */
public class GlobalFileException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GlobalFileException() {
        super();
    }

    public GlobalFileException(String message) {
        super(message);
    }

    public GlobalFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalFileException(Throwable cause) {
        super(cause);
    }

    protected GlobalFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
