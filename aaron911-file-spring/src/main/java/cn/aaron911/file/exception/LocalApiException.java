package cn.aaron911.file.exception;

/**
 * @version 1.0
 */
public class LocalApiException extends GlobalFileException {
	private static final long serialVersionUID = 1L;

	public LocalApiException() {
        super();
    }

    public LocalApiException(String message) {
        super(message);
    }

    public LocalApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalApiException(Throwable cause) {
        super(cause);
    }
}
