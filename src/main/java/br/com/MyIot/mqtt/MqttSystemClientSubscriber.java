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

/**
 * A classe <b>MqttStandardClient</b> é uma classe service responsável por fazer a inscrição no tópico mqtt responsável
 * por receber as mensagens a serem persistidas no banco de dados
 * <b>MqttStandardClient</b>
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class MqttSystemClientSubscriber implements MqttCallback {

	@Value("${mqtt.uri}")
	private String uri;
	
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
	
	/**
	 * Método que conecta ao broker mqtt e se inscreve no tópico que receberá as mensagens a serem persistidas no banco de dados
	 * @return Retorna a própria classe para permitir o encadeamento de métodos
	 */
	public MqttSystemClientSubscriber connect() {
		mqttClientService.create(username, password);
		try {
			MqttClient client = new MqttClient(uri, "f551c60c-c82e-4a8a-b591-b24e3ddf5235", new MqttDefaultFilePersistence());
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
					client.subscribe(MqttTopic.getSystemTopicToSubscribe(), 1);
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

	/**
	 * Método implementado da interface <b>MqttCallback</b> que é chamado quando a conexão é perdida, e assim chama novamente o
	 * método conect() para reconectar
	 */
	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost, cause: " + cause);
		this.connect();
	}

	/**
	 * Método implementado da interface <b>MqttCallback</b> que é chamado quando uma mensagem é recebida, então essa mensagem é
	 * analisada e reenviada para as classes responsáveis por fazer a conversão da mensagem e então persistir no banco de dados
	 */
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
				if(convertedMessage != null) {
					MeasuredValueForm form = new MeasuredValueForm(deviceId, convertedMessage.getValues(),
							convertedMessage.getTimestamp());
					measuredValueService.mqttCreate(form);
				}
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println(token);
	}

}