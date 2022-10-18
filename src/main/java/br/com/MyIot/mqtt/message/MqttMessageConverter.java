package br.com.MyIot.mqtt.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

/**
 * A classe <b>MqttMessageConverter</b> é responsável por converter um objeto do
 * tipo <b>MqttMessage</b> em <b>AnalogOutputDeviceMqttMessage</b>,
 * <b>DiscreteDeviceMqttMessage</b> ou <b>MeasuredValueMqttMessage</b>
 * 
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class MqttMessageConverter {

	/**
	 * Método que recebe um objeto do tipo <b>MqttMessage</b> e converte para um
	 * objeto do tipo <b>AnalogOutputDeviceMqttMessage</b>
	 * 
	 * @param message
	 * @return Retorna um objeto do tipo <b>AnalogOutputDeviceMqttMessage</b>
	 */
	public AnalogOutputDeviceMqttMessage toAnalogOutputDeviceMqttMessage(MqttMessage message) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(message.toString(), AnalogOutputDeviceMqttMessage.class);
		} catch (RuntimeException e) {
			return null;
		}

	}

	/**
	 * Método que recebe um objeto do tipo <b>MqttMessage</b> e converte para um
	 * objeto do tipo <b>DiscreteDeviceMqttMessage</b>
	 * 
	 * @param message
	 * @return Retorna um objeto do tipo <b>DiscreteDeviceMqttMessage</b>
	 */
	public DiscreteDeviceMqttMessage toDiscreteDeviceMqttMessage(MqttMessage message) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(message.toString(), DiscreteDeviceMqttMessage.class);
		} catch (RuntimeException e) {
			return null;
		}

	}

	/**
	 * Método que recebe um objeto do tipo <b>MeasuredValueMqttMessage</b> e
	 * converte para um objeto do tipo <b>AnalogOutputDeviceMqttMessage</b>
	 * 
	 * @param message
	 * @return Retorna um objeto do tipo <b>MeasuredValueMqttMessage</b>
	 */
	public MeasuredValueMqttMessage toMeasuredValueMqttMessage(MqttMessage message) {
		try {
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LocalDateTime.class,
							(JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime
									.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
					.create();
			return gson.fromJson(message.toString(), MeasuredValueMqttMessage.class);
		} catch (Exception e) {
			return null;
		}

	}

}
