package br.com.MyIot.mqtt;

import br.com.MyIot.model.device.Device;

/**
 * A classe <b>MqttStandardClient</b> define um padrão para os tópicos utilizados no Broker Mqtt
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class MqttTopic {

	public static String getDeviceTopic(Device device) {
		return new String("iot/" + device.getClass().getSimpleName() + "/" + device.getId());
	}
	
	public static String getDeviceTopic(Class<?> clazz, String deviceId) {
		return new String("iot/" + clazz.getSimpleName() + "/" + deviceId);
	}

	public static String getDeviceTopicToPersit(Device device) {
		return new String("iot/" + device.getClass().getSimpleName() + "/" + device.getId() + "/persist");
	}

	public static String getSystemTopicToSubscribe() {
		return new String("iot/+/+/persist");
	}
	
	public static String getSystemTopicToPublish() {
		return new String("iot/#");
	}
}
