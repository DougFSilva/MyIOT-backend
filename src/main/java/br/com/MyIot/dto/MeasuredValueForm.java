package br.com.MyIot.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MeasuredValueForm {

	private String deviceId;

	private List<Double> values;

	private LocalDateTime timestamp;
	
	public MeasuredValueForm() {
		super();
	}
	
	public MeasuredValueForm(String deviceId, List<Double> values, LocalDateTime timestamp) {
		this.deviceId = deviceId;
		this.values = values;
		this.timestamp = timestamp;
	}

	public MeasuredValueForm(String deviceId, List<Double> values) {
		this.deviceId = deviceId;
		this.values = values;
		this.timestamp = LocalDateTime.now();
	}

	public String getDeviceId() {
		return deviceId;
	}

	public List<Double> getValues() {
		return values;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "MeasuredValueForm [deviceId=" + deviceId + ", values=" + values + ", timestamp=" + timestamp + "]";
	}

}
