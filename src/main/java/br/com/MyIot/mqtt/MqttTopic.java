package br.com.MyIot.mqtt;

import br.com.MyIot.model.device.Device;

public class MqttTopic {

	public static String getDeviceTopic(Device device) {
		return new String("iot/" + device.getClass().getSimpleName() + "/" + device.getId());
	}

	public static String getDeviceTopicToPersit(Device device) {
		return new String("iot/" + device.getClass().getSimpleName() + "/" + device.getId() + "/persist");
	}

	public static String getSystemTopic() {
		return new String("iot/+/+/persist");
	}
}
