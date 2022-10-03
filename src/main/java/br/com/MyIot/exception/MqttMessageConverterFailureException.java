package br.com.MyIot.exception;

/**
 * A classe <b>MqttMessageConverterFailureException</b> representa uma exception que deve ser lançada quando acontecer um 
 * erro relacionado a falha de conversão de uma mensagem Mqtt do tipo <b>MqttMessage</b>
 * @since Out 2022
 * @version 1.0
 */
public class MqttMessageConverterFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MqttMessageConverterFailureException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MqttMessageConverterFailureException(String message) {
		super(message);
	}

}
