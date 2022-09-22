package br.com.MyIot.exception;

public class UnknownHostException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public UnknownHostException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownHostException(String message) {
		super(message);
	}

}
