package exceptions;

public class SupervisorNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public SupervisorNotFoundException() {
	}

	public SupervisorNotFoundException(String message) {
		super(message);
	}

	public SupervisorNotFoundException(Throwable cause) {
		super(cause);
	}

	public SupervisorNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SupervisorNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
