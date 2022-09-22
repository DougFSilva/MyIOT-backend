package br.com.MyIot.exception;

public class MqttFailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MqttFailException(String message, Throwable cause) {
		super(message, cause);
	}

	public MqttFailException(String message) {
		super(message);
	}
}
