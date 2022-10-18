package br.com.MyIot.mqtt.message;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A classe <b>MeasuredValueMqttMessage</b> é um modelo de mensagem apropriado
 * para ser utilizado para criar um <b>MeasuredValue</b> no banco de dados;
 * 
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class MeasuredValueMqttMessage {

	private LocalDateTime timestamp;

	private List<Double> values;

	public MeasuredValueMqttMessage(LocalDateTime timestamp, List<Double> values) {
		this.timestamp = timestamp;
		this.values = values;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "MeasuredValueMqttMessage [timestamp=" + timestamp + ", values=" + values + "]";
	}

}