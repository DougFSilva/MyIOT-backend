package br.com.MyIot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.DiscreteDeviceDto;
import br.com.MyIot.dto.device.DiscreteDeviceForm;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.exception.UserNotApprovedException;
import br.com.MyIot.model.device.DevicesPerUserValidate;
import br.com.MyIot.model.device.DiscreteDevice;
import br.com.MyIot.model.device.DiscreteDeviceRepository;
import br.com.MyIot.model.user.User;
import br.com.MyIot.mqtt.MqttDeviceRoleService;

@Service
public class DiscreteDeviceService {

	@Autowired
	private DiscreteDeviceRepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private MqttDeviceRoleService mqttDeviceRoleService;

	public String create(DiscreteDeviceForm form) {
		User user = userService.findById(form.getUserId());
		if (!user.isApprovedRegistration()) {
			throw new UserNotApprovedException("User " + user.getName() + " not approved!");
		}
		Integer numberOfDevices = repository.findAllByUser(user).size();
		DevicesPerUserValidate devicesPerUserValidate = new DevicesPerUserValidate(100, 10, 5);
		devicesPerUserValidate.validate(user, numberOfDevices);
		DiscreteDevice device = form.toDevice(user);
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

	public DiscreteDeviceDto updateById(String id, DiscreteDeviceForm form) {
		DiscreteDevice device = findById(id);
		device.setLocation(form.getLocation());
		device.setName(form.getName());
		device.setStatus(form.isStatus());
		return new DiscreteDeviceDto(repository.update(device));
	}
	
	public DiscreteDeviceDto updateStatusById(String id, boolean status) {
		DiscreteDevice device = findById(id);
		device.setStatus(status);
		return new DiscreteDeviceDto(repository.update(device));
	}

	public DiscreteDeviceDto findByIdDto(String id) {
		return new DiscreteDeviceDto(findById(id));
	}

	public DiscreteDevice findById(String id) {
		Optional<DiscreteDevice> device = repository.findById(id);
		return device
				.orElseThrow(() -> new ObjectNotFoundException("Device with id " + id + " not found in database!"));
	}

	public List<DiscreteDeviceDto> findAllByUser(String userId) {
		User user = userService.findById(userId);
		return repository.findAllByUser(user).stream().map(device -> new DiscreteDeviceDto(device)).toList();
	}

	public List<DiscreteDeviceDto> findAll() {
		return repository.findAll().stream().map(device -> new DiscreteDeviceDto(device)).toList();
	}

}
