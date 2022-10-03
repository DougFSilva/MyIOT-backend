package br.com.MyIot.exception;

/**
 * A classe <b>InvalidEmailException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro de email inválido
 * @since Out 2022
 * @version 1.0
 */
public class InvalidEmailException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidEmailException(String message) {
		super(message);
	}
}
