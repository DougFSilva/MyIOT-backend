package br.com.MyIot.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.MyIot.exception.MqttFailException;

@Service
public class MqttSystemClientPublisher {

	@Value("${mqtt.uri}")
	private String uri;
	
	@Value("${mqtt.system.username}")
	private String username;

	@Value("${mqtt.system.password}")
	private String password;
	
	public void publish(String topic, MqttMessage message) {
		try {
			MqttClient client = new MqttClient(uri, "",  new MqttDefaultFilePersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(username);
			options.setPassword(password.toCharArray());
			options.setCleanSession(true);
			options.setConnectionTimeout(0);
			options.setAutomaticReconnect(true);
			client.connect(options);
			if (client.isConnected()) {
				try {
					client.publish(topic, message);
				} catch (MqttException e) {
					e.printStackTrace();
					throw new MqttFailException("Impossible publish, cause: " + e.getMessage());
				}finally {
					client.disconnect();
				}
			}
		} catch (MqttException e) {
			throw new MqttFailException("Connection failure!, cause: " + e.getMessage());
		}
	}
}
