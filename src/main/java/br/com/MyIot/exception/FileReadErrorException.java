package br.com.MyIot.exception;

/**
 * A classe <b>FileReadErrorException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro de falha na leitura de arquivo
 * @since Out 2022
 * @version 1.0
 */
public class FileReadErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public FileReadErrorException(String message) {
		super(message);
	}
	
	public FileReadErrorException(String message, Throwable cause) {
		super(message, cause);
	}

}
