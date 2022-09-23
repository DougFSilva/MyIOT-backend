package br.com.MyIot.mqtt.message;

public class DiscreteDeviceMqttMessage {

	private boolean status;

	public DiscreteDeviceMqttMessage(boolean status) {
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
