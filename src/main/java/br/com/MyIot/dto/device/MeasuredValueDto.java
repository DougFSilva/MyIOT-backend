package br.com.MyIot.dto.device;

import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.MyIot.model.device.MeasuringDevice.MeasuredValue;

public class MeasuredValueDto {

	private String id;

	private String deviceId;

	private List<Double> values;

	private String timestamp;

	public MeasuredValueDto(MeasuredValue measuredValue) {
		this.id = measuredValue.getId();
		this.deviceId = measuredValue.getDevice().getId();
		this.values = measuredValue.getValues();
		this.timestamp = measuredValue.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
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

	public String getTimestamp() {
		return timestamp;
	}

}
