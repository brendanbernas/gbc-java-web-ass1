package service.report;

public class SectionNotUniqueException extends Exception {

	public SectionNotUniqueException() {
	}

	public SectionNotUniqueException(String message) {
		super(message);
	}

	public SectionNotUniqueException(Throwable cause) {
		super(cause);
	}

	public SectionNotUniqueException(String message, Throwable cause) {
		super(message, cause);
	}

	public SectionNotUniqueException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
