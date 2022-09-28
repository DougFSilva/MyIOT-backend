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

@Service
public class WebSocketMessager {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public void sendMessage(MeasuredValue measuredValue) {
		String username = measuredValue.getDevice().getUser().getUsername();
		messagingTemplate.convertAndSendToUser(username, "/queue/message", new MeasuredValueDto(measuredValue));
	}
	
	public void sendMessage(AnalogOutputDevice device) {
		String username = device.getUser().getUsername();
		messagingTemplate.convertAndSendToUser(username, "/queue/message", new AnalogOutputDeviceDto(device));
	}
	
	public void sendMessage(DiscreteDevice device) {
		String username = device.getUser().getUsername();
		messagingTemplate.convertAndSendToUser(username, "/queue/message", new DiscreteDeviceDto(device));
	}
	
}
