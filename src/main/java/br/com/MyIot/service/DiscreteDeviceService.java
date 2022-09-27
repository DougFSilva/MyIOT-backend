package br.com.MyIot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.DiscreteDeviceDto;
import br.com.MyIot.dto.device.DiscreteDeviceForm;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.exception.OperationNotAllowedException;
import br.com.MyIot.exception.UserNotApprovedException;
import br.com.MyIot.model.device.discreteDevice.DiscreteDevice;
import br.com.MyIot.model.device.discreteDevice.DiscreteDevicePerUser;
import br.com.MyIot.model.device.discreteDevice.DiscreteDeviceRepository;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
import br.com.MyIot.mqtt.MqttDeviceRoleService;

@Service
public class DiscreteDeviceService {

	@Autowired
	private DiscreteDeviceRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MqttDeviceRoleService mqttDeviceRoleService;
	
	@Autowired
	private DiscreteDevicePerUser devicePerUser;

	public String create(DiscreteDeviceForm form) {
		User autenticatedUser = getAuthenticatedUser();
		User user = userRepository.findById(autenticatedUser.getId()).get();
		if (!user.isApprovedRegistration()) {
			throw new UserNotApprovedException("User " + user.getName() + " not approved!");
		}
		Integer numberOfDevices = findAllByUser().size();
		devicePerUser.validate(user, numberOfDevices);
		DiscreteDevice device = form.toDevice(user);
		String createdDeviceId = repository.create(device);
		device.setId(createdDeviceId);
		mqttDeviceRoleService.create(device);
		return createdDeviceId;
	}

	public void deleteById(String id) {
		DiscreteDevice device = findById(id);
		repository.delete(device);
		return;
	}

	public void deleteAllByUser() {
		User user = getAuthenticatedUser();
		repository.deleteAllByUser(user);
		return;
	}

	public DiscreteDeviceDto updateById(String id, DiscreteDeviceForm form) {
		DiscreteDevice device = findById(id);
		device.setLocation(form.getLocation());
		device.setName(form.getName());
		return new DiscreteDeviceDto(repository.update(device));
	}
	
	public DiscreteDeviceDto updateStatusById(String id, boolean status) {
		Optional<DiscreteDevice> device = repository.findById(id);
		if(device.isEmpty()) {
			throw new ObjectNotFoundException("Device with id " + id + " not found in database!");
		}
		device.get().setStatus(status);
		return new DiscreteDeviceDto(repository.update(device.get()));
	}

	public DiscreteDeviceDto findByIdDto(String id) {
		return new DiscreteDeviceDto(findById(id));
	}

	public DiscreteDevice findById(String id) {
		Optional<DiscreteDevice> device = repository.findById(id);
		if(device.isPresent() && !device.get().getUser().equals(getAuthenticatedUser())) {
			throw new OperationNotAllowedException("User without permission to execute this operation!");
		}
		return device
				.orElseThrow(() -> new ObjectNotFoundException("Device with id " + id + " not found in database!"));
	}

	public List<DiscreteDeviceDto> findAllByUser() {
		User user = getAuthenticatedUser();
		return repository.findAllByUser(user).stream().map(device -> new DiscreteDeviceDto(device)).toList();
	}

	public List<DiscreteDeviceDto> findAll() {
		return repository.findAll().stream().map(device -> new DiscreteDeviceDto(device)).toList();
	}
	
	private User getAuthenticatedUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
