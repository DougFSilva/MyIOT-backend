package br.com.MyIot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import br.com.MyIot.exception.MqttFailException;

public class MqttPublisher {
	
	private String uri;

	private String username;

	private String password;

	public MqttPublisher setCredentials(String uri, String username, String password) {
		this.uri = uri;
		this.username = username;
		this.password = password;
		return this;
	}

	public void publish(String topic, String payload, Integer qos, String clientId) {
		try {
			MqttClient client = new MqttClient(uri, clientId, new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(true);
			options.setUserName(this.username);
			options.setPassword(this.password.toCharArray());
			client.connect(options);
			MqttMessage message = new MqttMessage(payload.getBytes());
			message.setQos(qos);
			message.setRetained(false);
			client.publish(topic, message);
			client.disconnect();
		} catch (MqttException e) {
			throw new MqttFailException("Impossible publish in topic " + topic + " :" + e.getMessage(), e.getCause());
		}
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
