package br.com.MyIot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.MeasuringDeviceDto;
import br.com.MyIot.dto.device.MeasuringDeviceForm;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.exception.OperationNotAllowedException;
import br.com.MyIot.exception.UserNotApprovedException;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevicePerUser;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDeviceRepository;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
import br.com.MyIot.mqtt.MqttDeviceRoleService;

@Service
public class MeasuringDeviceService {

	@Autowired
	private MeasuringDeviceRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MqttDeviceRoleService mqttDeviceRoleService;

	@Autowired
	private MeasuringDevicePerUser devicePerUser;

	public String create(MeasuringDeviceForm form) {
		User autenticatedUser = getAuthenticatedUser();
		User user = userRepository.findById(autenticatedUser.getId()).get();
		if (!user.isApprovedRegistration()) {
			throw new UserNotApprovedException("User " + user.getName() + " not approved!");
		}
		Integer numberOfDevices = findAllByUser().size();
		devicePerUser.validate(user, numberOfDevices);
		MeasuringDevice device = form.toDevice(user);
		String createdDeviceId = repository.create(device);
		device.setId(createdDeviceId);
		mqttDeviceRoleService.create(device);
		return createdDeviceId;
	}

	public void deleteById(String id) {
		MeasuringDevice device = findById(id);
		repository.delete(device);
		return;
	}

	public void deleteAllByUser() {
		User user = getAuthenticatedUser();
		repository.deleteAllByUser(user);
		return;
	}

	public MeasuringDeviceDto updateById(String id, MeasuringDeviceForm form) {
		MeasuringDevice device = findById(id);
		device.setLocation(form.getLocation());
		device.setName(form.getName());
		device.setKeyNames(form.getKeyNames());
		return new MeasuringDeviceDto(repository.update(device));
	}

	public MeasuringDeviceDto findByIdDto(String id) {
		return new MeasuringDeviceDto(findById(id));
	}

	public MeasuringDevice findById(String id) {
		Optional<MeasuringDevice> device = repository.findById(id);
		if(device.isPresent() && !device.get().getUser().equals(getAuthenticatedUser())) {
			throw new OperationNotAllowedException("User without permission to execute this operation!");
		}
		return device
				.orElseThrow(() -> new ObjectNotFoundException("Device with id " + id + " not found in database!"));
	}

	public List<MeasuringDeviceDto> findAllByUser() {
		User user = getAuthenticatedUser();
		return repository.findAllByUser(user).stream().map(device -> new MeasuringDeviceDto(device)).toList();
	}

	public List<MeasuringDeviceDto> findAll() {
		return repository.findAll().stream().map(device -> new MeasuringDeviceDto(device)).toList();
	}

	private User getAuthenticatedUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
