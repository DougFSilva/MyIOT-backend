package br.com.MyIot.mqtt.message;

import java.time.LocalDateTime;
import java.util.List;

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

}
