package exceptions;

public class TextOverflowException extends Exception {

	private static final long serialVersionUID = 1L;

	public TextOverflowException() {
	}

	public TextOverflowException(String message) {
		super(message);
	}

	public TextOverflowException(Throwable cause) {
		super(cause);
	}

	public TextOverflowException(String message, Throwable cause) {
		super(message, cause);
	}

	public TextOverflowException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
