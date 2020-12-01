package exceptions;

public class RepresentationInvariantException extends Exception {

	private static final long serialVersionUID = 1L;

	public RepresentationInvariantException() {
	}

	public RepresentationInvariantException(String message) {
		super(message);
	}

	public RepresentationInvariantException(Throwable cause) {
		super(cause);
	}

	public RepresentationInvariantException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepresentationInvariantException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
