package br.com.MyIot.exception;

/**
 * A classe <b>UnknownHostException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro relacionado a alguma operação nao permitida que foi tentado realizar
 * @since Out 2022
 * @version 1.0
 */
public class OperationNotAllowedException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public OperationNotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public OperationNotAllowedException(String message) {
		super(message);
	}
}
