package br.com.MyIot.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.MeasuredValueForm;
import br.com.MyIot.exception.MqttFailException;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDevice;
import br.com.MyIot.model.device.discreteDevice.DiscreteDevice;
import br.com.MyIot.mqtt.message.AnalogOutputDeviceMqttMessage;
import br.com.MyIot.mqtt.message.DiscreteDeviceMqttMessage;
import br.com.MyIot.mqtt.message.MeasuredValueMqttMessage;
import br.com.MyIot.mqtt.message.MqttMessageConverter;
import br.com.MyIot.service.AnalogOutputDeviceService;
import br.com.MyIot.service.DiscreteDeviceService;
import br.com.MyIot.service.MeasuredValueService;

@Service
public class MqttSystemClientSubscriber implements MqttCallback {

	@Value("${mqtt.uri}")
	private String uri;
	
	@Value("${mqtt.system.clientId}")
	private String clientId;

	@Value("${mqtt.system.username}")
	private String username;

	@Value("${mqtt.system.password}")
	private String password;
	
	@Autowired
	private AnalogOutputDeviceService analogOutputDeviceService;

	@Autowired
	private DiscreteDeviceService discreteDeviceService;

	@Autowired
	private MeasuredValueService measuredValueService;
	
	@Autowired 
	private MqttSystemClientService mqttClientService;
	
	public MqttSystemClientSubscriber connect() {
		mqttClientService.create(clientId, username, password);
		try {
			MqttClient client = new MqttClient(uri, clientId, new MqttDefaultFilePersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(username);
			options.setPassword(password.toCharArray());
			options.setCleanSession(true);
			options.setConnectionTimeout(0);
			options.setAutomaticReconnect(true);
			client.connect(options);
			if (client.isConnected()) {
				System.out.println("Mqtt Connected!");
				try {
					client.subscribe(MqttTopic.getSystemTopic(), 1);
				} catch (MqttException e) {
					throw new MqttFailException("Impossible subscribe, cause: " + e.getMessage());
				}
				client.setCallback(this);
			}
		} catch (MqttException e) {
			throw new MqttFailException("Connection failure!, cause: " + e.getMessage());
		}
		return this;
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost, cause: " + cause);
		this.connect();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String[] topicSplit = topic.split("/");
		String deviceType = topicSplit[1];
		String deviceId = topicSplit[2];
		if (deviceType.equals(AnalogOutputDevice.class.getSimpleName())) {
				MqttMessageConverter converter = new MqttMessageConverter();
				AnalogOutputDeviceMqttMessage convertedMessage = converter.toAnalogOutputDeviceMqttMessage(message);
				analogOutputDeviceService.updateOutputById(deviceId, convertedMessage.getOutput());
		}

		if (deviceType.equals(DiscreteDevice.class.getSimpleName())) {
				MqttMessageConverter converter = new MqttMessageConverter();
				DiscreteDeviceMqttMessage convertedMessage = converter.toDiscreteDeviceMqttMessage(message);
				discreteDeviceService.updateStatusById(deviceId, convertedMessage.isStatus());
		}

		if (deviceType.equals(MeasuringDevice.class.getSimpleName())) {
				MqttMessageConverter converter = new MqttMessageConverter();
				MeasuredValueMqttMessage convertedMessage = converter.toMeasuredValueMqttMessage(message);
				MeasuredValueForm form = new MeasuredValueForm(deviceId, convertedMessage.getValues(),
						convertedMessage.getTimestamp());
				measuredValueService.mqttCreate(form);
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}

}
