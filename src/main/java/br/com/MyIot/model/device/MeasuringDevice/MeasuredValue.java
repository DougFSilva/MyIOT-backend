package br.com.MyIot.model.device.MeasuringDevice;

import java.time.LocalDateTime;
import java.util.List;
/**
 * A Classe <b>MeasuredValue</b> define os valores de medição pertencentes a um dispositivo do tipo <b>MeasuringDevice</b>
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class MeasuredValue {

	private String id;

	private MeasuringDevice device;

	private List<Double> values;

	private LocalDateTime timestamp;

	public MeasuredValue(String id, MeasuringDevice device, List<Double> values, LocalDateTime timestamp) {
		this.id = id;
		this.device = device;
		this.values = values;
		this.timestamp = timestamp;
	}
	
	public MeasuredValue(MeasuringDevice device, List<Double> values, LocalDateTime timestamp) {
		this.device = device;
		this.values = values;
		if(timestamp == null) {
			this.timestamp = LocalDateTime.now();
		}else {
			this.timestamp = timestamp;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MeasuringDevice getDevice() {
		return device;
	}

	public void setDevice(MeasuringDevice device) {
		this.device = device;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timeStamp) {
		this.timestamp = timeStamp;
	}

	@Override
	public String toString() {
		return "MeasuredValue [id=" + id + ", device=" + device + ", values=" + values + ", timeStamp=" + timestamp
				+ "]";
	}

}
