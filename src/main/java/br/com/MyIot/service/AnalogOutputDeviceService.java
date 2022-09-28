package br.com.MyIot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.AnalogOutputDeviceDto;
import br.com.MyIot.dto.device.AnalogOutputDeviceForm;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.exception.OperationNotAllowedException;
import br.com.MyIot.exception.UserNotApprovedException;
import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDevice;
import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDevicePerUser;
import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDeviceRepository;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
import br.com.MyIot.mqtt.MqttDeviceRoleService;

@Service
public class AnalogOutputDeviceService {

	@Autowired
	private AnalogOutputDeviceRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MqttDeviceRoleService mqttDeviceRoleService;
	
	@Autowired
	private AnalogOutputDevicePerUser devicesPerUser;
	
	@Autowired
	private WebSocketMessager messager;
	
	public String create(AnalogOutputDeviceForm form) {
		User autenticatedUser = getAuthenticatedUser();
		User user = userRepository.findById(autenticatedUser.getId()).get();
		if (!user.isApprovedRegistration()) {
			throw new UserNotApprovedException("User " + user.getName() + " not approved!");
		}
		Integer numberOfDevices = findAllByUser().size();
		devicesPerUser.validate(user, numberOfDevices);
		AnalogOutputDevice device = form.toDevice(user);
		String createdDeviceId = repository.create(device);
		device.setId(createdDeviceId);
		mqttDeviceRoleService.create(device);
		return createdDeviceId;
	}

	public void deleteById(String id) {
		AnalogOutputDevice device = findById(id);
		repository.delete(device);
		return;
	}

	public void deleteAllByUser() {
		User user = getAuthenticatedUser();
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
		Optional<AnalogOutputDevice> device = repository.findById(id);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException("Device with id " + id + " not found in database!");
		}
		device.get().setOutput(output);
		AnalogOutputDevice updatedDevice = repository.update(device.get());
		messager.sendMessage(updatedDevice);
		return new AnalogOutputDeviceDto(updatedDevice);
	}
	
	public AnalogOutputDeviceDto findByIdDto(String id) {
		return new AnalogOutputDeviceDto(findById(id));
	}

	public AnalogOutputDevice findById(String id) {
		Optional<AnalogOutputDevice> device = repository.findById(id);
		if(device.isPresent() && !device.get().getUser().equals(getAuthenticatedUser())) {
			throw new OperationNotAllowedException("User without permission to execute this operation!");
		}
		return device
				.orElseThrow(() -> new ObjectNotFoundException("Device with id " + id + " not found in database!"));
	}
	
	public List<AnalogOutputDeviceDto> findAllByUser() {
		User user = getAuthenticatedUser();
		return repository.findAllByUser(user).stream().map(device -> new AnalogOutputDeviceDto(device)).toList();
	}
	
	public List<AnalogOutputDeviceDto> findAll() {
		return repository.findAll().stream().map(device -> new AnalogOutputDeviceDto(device)).toList();
	}
	
	private User getAuthenticatedUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
