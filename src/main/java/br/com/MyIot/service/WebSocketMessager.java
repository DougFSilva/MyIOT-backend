package br.com.MyIot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.AnalogOutputDeviceDto;
import br.com.MyIot.dto.device.DiscreteDeviceDto;
import br.com.MyIot.dto.device.MeasuredValueDto;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValue;
import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDevice;
import br.com.MyIot.model.device.discreteDevice.DiscreteDevice;

/**
 * A classe <b>WebSocketMessager</b> é uma classe service responsável por enviar os dados recebidos para os usuários por websocket
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class WebSocketMessager {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	/**
	 * Recebe um objeto do tipo <b>MeasuredValue</b> e encaminha um Dto do mesmo para o usuário a qual pertence por meio de 
	 * websocket
	 * @param measuredValue
	 */
	public void sendMessage(MeasuredValue measuredValue) {
		String username = measuredValue.getDevice().getUser().getUsername();
		messagingTemplate.convertAndSendToUser(username, "/queue/message/measuring-device", 
				new MeasuredValueDto(measuredValue));
	}
	
	/**
	 * Recebe um objeto do tipo <b>Device</b> e encaminha um <b>AnalogOutuptDeviceDto</b> para o usuário a qual pertence
	 * por meio de websocket
	 * @param device
	 */
	public void sendMessage(AnalogOutputDevice device) {
		String username = device.getUser().getUsername();
		messagingTemplate.convertAndSendToUser(username, "/queue/message/analog-output-device", new AnalogOutputDeviceDto(device));
	}
	
	/**
	 * Recebe um objeto do tipo <b>Device</b> e encaminha um <b>DiscreteDeviceDto</b> para o usuário a qual pertence
	 * por meio de websocket
	 * @param device
	 */
	public void sendMessage(DiscreteDevice device) {
		String username = device.getUser().getUsername();
		messagingTemplate.convertAndSendToUser(username, "/queue/message/discrete-device", new DiscreteDeviceDto(device));
	}
	
}
