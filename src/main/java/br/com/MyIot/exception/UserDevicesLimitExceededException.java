package br.com.MyIot.exception;

public class UserDevicesLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserDevicesLimitExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserDevicesLimitExceededException(String message) {
		super(message);
	}

}
