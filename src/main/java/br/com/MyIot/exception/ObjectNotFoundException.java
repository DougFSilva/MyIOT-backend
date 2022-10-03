package br.com.MyIot.exception;

/**
 * A classe <b>ObjectNotFoundException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro de objeto não encontrado
 * @since Out 2022
 * @version 1.0
 */
public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public ObjectNotFoundException(String message) {
		super(message);
	}
}
