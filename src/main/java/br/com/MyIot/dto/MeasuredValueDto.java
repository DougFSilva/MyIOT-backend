package br.com.MyIot.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.MyIot.model.device.MeasuredValue;

public class MeasuredValueDto {

	private String id;

	private String deviceId;

	private List<Double> values;

	private LocalDateTime timestamp;

	public MeasuredValueDto(MeasuredValue measuredValue) {
		this.id = measuredValue.getId();
		this.deviceId = measuredValue.getDevice().getId();
		this.values = measuredValue.getValues();
		this.timestamp = measuredValue.getTimestamp();
	}

	public String getId() {
		return id;
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

}
