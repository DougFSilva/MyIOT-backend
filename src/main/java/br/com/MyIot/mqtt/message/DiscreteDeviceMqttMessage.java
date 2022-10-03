package br.com.MyIot.mqtt.message;

/**
 * A classe <b>DiscreteDeviceMqttMessage</b> é um modelo de mensagem apropriado para ser utilizado para atualizar o status de
 * um <b>DiscreteDevice</b> no banco de dados;
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
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
