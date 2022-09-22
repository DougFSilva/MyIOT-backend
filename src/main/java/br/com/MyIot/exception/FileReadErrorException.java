package br.com.MyIot.exception;

public class FileReadErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public FileReadErrorException(String message) {
		super(message);
	}
	
	public FileReadErrorException(String message, Throwable cause) {
		super(message, cause);
	}

}
