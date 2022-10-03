package br.com.MyIot.exception;

/**
 * A classe <b>MqttFailException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro relacionado a uma falha Mqtt
 * @since Out 2022
 * @version 1.0
 */
public class MqttFailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MqttFailException(String message, Throwable cause) {
		super(message, cause);
	}

	public MqttFailException(String message) {
		super(message);
	}
}
