package br.com.MyIot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import br.com.MyIot.exception.MqttFailException;

public class MqttSubscriber {

	private String uri;

	private String username;

	private String password;

	private MqttClient client;

	public MqttSubscriber setCredentials(String uri, String username, String password) {
		this.uri = uri;
		this.username = username;
		this.password = password;
		return this;
	}

	public void subscribe(String clientId, String topic, Integer qos, MqttCallback mqttCallback) {
		try {
			this.client = new MqttClient(uri, clientId, new MqttDefaultFilePersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(username);
			options.setPassword(password.toCharArray());
			options.setCleanSession(true);
			client.connect(options);
			client.subscribe(topic, qos);
			client.setCallback(mqttCallback);
		} catch (MqttException e) {
			throw new MqttFailException("Impossible subscribe in topic " + topic + " :" + e.getMessage(), e.getCause());
		}
	}

	public void unsubscribe(String topic) {
		try {
			this.client.unsubscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

}
