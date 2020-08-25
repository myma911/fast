package cn.aaron911.file.exception;

/**
 * @version 1.0
 */
public class MinIoApiException extends GlobalFileException {
	private static final long serialVersionUID = 1L;

	public MinIoApiException() {
        super();
    }

    public MinIoApiException(String message) {
        super(message);
    }

    public MinIoApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinIoApiException(Throwable cause) {
        super(cause);
    }
}
