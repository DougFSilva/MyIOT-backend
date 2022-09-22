package br.com.MyIot.exception;

public class MqttMessageConverterFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MqttMessageConverterFailureException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MqttMessageConverterFailureException(String message) {
		super(message);
	}

}
