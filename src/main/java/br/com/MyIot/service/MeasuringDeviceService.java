package br.com.MyIot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.MeasuringDeviceDto;
import br.com.MyIot.dto.MeasuringDeviceForm;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.exception.UserNotApprovedException;
import br.com.MyIot.model.device.DevicesPerUserValidate;
import br.com.MyIot.model.device.MeasuringDevice;
import br.com.MyIot.model.device.MeasuringDeviceRepository;
import br.com.MyIot.model.user.User;
import br.com.MyIot.mqtt.MqttDeviceRoleService;

@Service
public class MeasuringDeviceService {

	@Autowired
	private MeasuringDeviceRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MqttDeviceRoleService mqttDeviceRoleService;
	
	public String create(MeasuringDeviceForm form) {
		User user = userService.findById(form.getUserId());
		if (!user.isApprovedRegistration()) {
			throw new UserNotApprovedException("User " + user.getName() + " not approved!");
		}
		Integer numberOfDevices = repository.findAllByUser(user).size();
		DevicesPerUserValidate devicesPerUserValidate = new DevicesPerUserValidate(100, 10, 5);
		devicesPerUserValidate.validate(user, numberOfDevices);
		MeasuringDevice device = form.toDevice(user);
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

	public MeasuringDeviceDto updateById(String id, MeasuringDeviceForm form) {
		MeasuringDevice device = findById(id);
		device.setLocation(form.getLocation());
		device.setName(form.getName());
		device.setKeyNames(form.getKeyNames());
		return new MeasuringDeviceDto(repository.updateById(device));
	}

	public MeasuringDeviceDto findByIdDto(String id) {
		return new MeasuringDeviceDto(findById(id));
	}

	public MeasuringDevice findById(String id) {
		Optional<MeasuringDevice> device = repository.findById(id);
		return device
				.orElseThrow(() -> new ObjectNotFoundException("Device with id " + id + " not found in database!"));
	}

	public List<MeasuringDeviceDto> findAllByUser(String userId) {
		User user = userService.findById(userId);
		return repository.findAllByUser(user).stream().map(device -> new MeasuringDeviceDto(device)).toList();
	}

	public List<MeasuringDeviceDto> findAll() {
		return repository.findAll().stream().map(device -> new MeasuringDeviceDto(device)).toList();
	}
	
}
