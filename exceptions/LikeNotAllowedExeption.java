package exceptions;

public class LikeNotAllowedExeption extends Exception {

	private static final long serialVersionUID = 1L;

	public LikeNotAllowedExeption() {
	}

	public LikeNotAllowedExeption(String message) {
		super(message);
	}

	public LikeNotAllowedExeption(Throwable cause) {
		super(cause);
	}

	public LikeNotAllowedExeption(String message, Throwable cause) {
		super(message, cause);
	}

	public LikeNotAllowedExeption(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
