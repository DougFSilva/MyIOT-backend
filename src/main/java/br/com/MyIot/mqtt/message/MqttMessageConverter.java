package br.com.MyIot.mqtt.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import br.com.MyIot.exception.MqttMessageConverterFailureException;

public class MqttMessageConverter {

	public AnalogOutputDeviceMqttMessage toAnalogOutputDeviceMqttMessage(MqttMessage message) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(message.toString(), AnalogOutputDeviceMqttMessage.class);
		} catch (Exception e) {
			throw new MqttMessageConverterFailureException(e.getMessage(), e.getCause());
		}

	}

	public DiscreteDeviceMqttMessage toDiscreteDeviceMqttMessage(MqttMessage message) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(message.toString(), DiscreteDeviceMqttMessage.class);
		} catch (Exception e) {
			throw new MqttMessageConverterFailureException(e.getMessage(), e.getCause());
		}

	}

	public MeasuredValueMqttMessage toMeasuredValueMqttMessage(MqttMessage message) {
		try {
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(LocalDateTime.class,
							(JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime
									.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
					.create();
			return gson.fromJson(message.toString(), MeasuredValueMqttMessage.class);
		} catch (Exception e) {
			throw new MqttMessageConverterFailureException(e.getMessage(), e.getCause());
		}

	}

}
