package br.com.MyIot.exception;
public class UserNotApprovedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotApprovedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotApprovedException(String message) {
		super(message);
	}
}
