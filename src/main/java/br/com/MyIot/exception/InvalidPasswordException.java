package br.com.MyIot.exception;

/**
 * A classe <b>InvalidPasswordException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro de password inválido
 * @since Out 2022
 * @version 1.0
 */
public class InvalidPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordException(String message) {
		super(message);
	}
}
