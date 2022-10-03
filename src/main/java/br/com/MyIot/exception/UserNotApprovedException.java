package br.com.MyIot.exception;

/**
 * A classe <b>UserNotApprovedException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro de usuário não aprovado
 * @since Out 2022
 * @version 1.0
 */
public class UserNotApprovedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotApprovedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotApprovedException(String message) {
		super(message);
	}
}
