package br.com.MyIot.exception;

/**
 * A classe <b>UserDevicesLimitExceededException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro de limite excedido de dispositivos cadastrados por usuário
 * @since Out 2022
 * @version 1.0
 */
public class UserDevicesLimitExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserDevicesLimitExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserDevicesLimitExceededException(String message) {
		super(message);
	}

}
