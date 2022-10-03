package br.com.MyIot.exception;

/**
 * A classe <b>DataIntegratyViolationException</b> representa uma exception que deve ser lançada quando acontecer um erro relacionado
 * a integridade dos dados
 * @since Out 2022
 * @version 1.0
 */
public class DataIntegratyViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegratyViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegratyViolationException(String message) {
		super(message);
	}

}
