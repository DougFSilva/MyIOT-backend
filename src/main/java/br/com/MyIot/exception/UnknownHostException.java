package br.com.MyIot.exception;

/**
 * A classe <b>UnknownHostException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro relacionado a host desconhecido
 * @since Out 2022
 * @version 1.0
 */
public class UnknownHostException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public UnknownHostException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownHostException(String message) {
		super(message);
	}

}
