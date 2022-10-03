package br.com.MyIot.mqtt.message;

/**
 * A classe <b>AnalogOutputDeviceMqttMessage</b> é um modelo de mensagem apropriado para ser utilizado para atualizar o output de
 * um <b>AnalogOutputDevice</b> no banco de dados;
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
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
