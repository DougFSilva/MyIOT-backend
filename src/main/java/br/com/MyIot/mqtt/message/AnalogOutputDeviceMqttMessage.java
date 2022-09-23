package br.com.MyIot.mqtt.message;

public class AnalogOutputDeviceMqttMessage {

	private Integer output;

	public AnalogOutputDeviceMqttMessage(Integer output) {
		this.output = output;
	}

	public Integer getOutput() {
		return output;
	}

	public void setOutput(Integer output) {
		this.output = output;
	}

}
