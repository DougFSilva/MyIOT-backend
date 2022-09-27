package br.com.MyIot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.AnalogOutputDeviceDto;
import br.com.MyIot.dto.device.AnalogOutputDeviceForm;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.exception.UserNotApprovedException;
import br.com.MyIot.model.device.AnalogOutputDevice;
import br.com.MyIot.model.device.AnalogOutputDeviceRepository;
import br.com.MyIot.model.device.DevicesPerUserValidate;
import br.com.MyIot.model.user.User;
import br.com.MyIot.mqtt.MqttDeviceRoleService;

@Service
public class AnalogOutputDeviceService {

	@Autowired
	private AnalogOutputDeviceRepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private MqttDeviceRoleService mqttDeviceRoleService;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public String create(AnalogOutputDeviceForm form) {
		User user = userService.findById(form.getUserId());
		if (!user.isApprovedRegistration()) {
			throw new UserNotApprovedException("User " + user.getName() + " not approved!");
		}
		Integer numberOfDevices = repository.findAllByUser(user).size();
		DevicesPerUserValidate devicesPerUserValidate = new DevicesPerUserValidate(100, 10, 5);
		devicesPerUserValidate.validate(user, numberOfDevices);
		AnalogOutputDevice device = form.toDevice(user);
		String createdDeviceId = repository.create(device);
		device.setId(createdDeviceId);
		mqttDeviceRoleService.create(device);
		return createdDeviceId;
	}

	public void deleteById(String id) {
		findById(id);
		repository.deleteById(id);
		return;
	}

	public void deleteAllByUser(String userId) {
		User user = userService.findById(userId);
		repository.deleteAllByUser(user);
		return;
	}

	public AnalogOutputDeviceDto updateById(String id, AnalogOutputDeviceForm form) {
		AnalogOutputDevice device = findById(id);
		device.setLocation(form.getLocation());
		device.setName(form.getName());
		return new AnalogOutputDeviceDto(repository.update(device));
	}
	
	public AnalogOutputDeviceDto updateOutputById(String id, Integer output) {
		AnalogOutputDevice device = findById(id);
		device.setOutput(output);
		AnalogOutputDevice updatedDevice = repository.update(device);
		messagingTemplate.convertAndSend("/topic/hi/user0@gmail.com", new AnalogOutputDeviceDto(updatedDevice));
		return new AnalogOutputDeviceDto();
	}
	
	public AnalogOutputDeviceDto findByIdDto(String id) {
		return new AnalogOutputDeviceDto(findById(id));
	}

	public AnalogOutputDevice findById(String id) {
		Optional<AnalogOutputDevice> device = repository.findById(id);
		return device
				.orElseThrow(() -> new ObjectNotFoundException("Device with id " + id + " not found in database!"));
	}

	public List<AnalogOutputDeviceDto> findAllByUser(String userId) {
		User user = userService.findById(userId);
		return repository.findAllByUser(user).stream().map(device -> new AnalogOutputDeviceDto(device)).toList();
	}
	
	public List<AnalogOutputDeviceDto> findAll() {
		return repository.findAll().stream().map(device -> new AnalogOutputDeviceDto(device)).toList();
	}

}
