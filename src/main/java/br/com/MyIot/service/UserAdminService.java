package br.com.MyIot.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.user.UserDto;
import br.com.MyIot.dto.user.UserAdminForm;
import br.com.MyIot.exception.DataIntegratyViolationException;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDeviceRepository;
import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDeviceRepository;
import br.com.MyIot.model.device.discreteDevice.DiscreteDeviceRepository;
import br.com.MyIot.model.user.Email;
import br.com.MyIot.model.user.Profile;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
import br.com.MyIot.mqtt.MqttStandardClient;
import br.com.MyIot.mqtt.MqttStandardClientService;
import br.com.MyIot.util.password.PasswordManager;

@Service
public class UserAdminService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private AnalogOutputDeviceRepository analogOutputDeviceRepository;

	@Autowired
	private DiscreteDeviceRepository discreteDeviceRepository;

	@Autowired
	private MeasuringDeviceRepository measuringDeviceRepository;

	@Autowired
	private MqttStandardClientService mqttStandardClientService;

	@Autowired
	private PasswordManager passwordManager;

	public String create(UserAdminForm form) {
		if (repository.findByEmail(new Email(form.getEmail())).isPresent()) {
			throw new DataIntegratyViolationException("Email " + form.getEmail() + " already exist in database!");
		}
		String password = passwordManager.validateAndEncode(form.getPassword());
		User user = form.toUser();
		user.setPassword(password);
		user.setClientMqttPassword(UUID.randomUUID().toString());
		String createdUserId = repository.create(user);
		mqttStandardClientService.create(new MqttStandardClient(user));
		return createdUserId;
	};

	public void deleteById(String id) {
		User user = findById(id);
		discreteDeviceRepository.deleteAllByUser(user);
		measuringDeviceRepository.deleteAllByUser(user);
		analogOutputDeviceRepository.deleteAllByUser(user);
		repository.delete(user);
		mqttStandardClientService.delete(new MqttStandardClient(user));
		return;
	}

	public UserDto updateById(String id, UserAdminForm form) {
		User user = findById(id);
		user.setName(form.getName());
		List<Profile> profiles = form.getProfiles().stream().map(profile -> new Profile(ProfileType.toEnum(profile)))
				.collect(Collectors.toList());
		user.setProfiles(profiles);
		return new UserDto(repository.update(user));
	}

	public UserDto findByIdDto(String id) {
		return new UserDto(findById(id));
	}

	public User findById(String id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found in database!"));
	}

	public UserDto findByEmailDto(String address) {
		return new UserDto(findByEmail(address));
	}

	public User findByEmail(String address) {
		Optional<User> user = repository.findByEmail(new Email(address));
		return user.orElseThrow(
				() -> new ObjectNotFoundException("User with email " + address + " not found in database!"));
	}

	public List<UserDto> findAll() {
		return repository.findAll().stream().map(user -> new UserDto(user)).toList();
	}

	public UserDto setApproveRegistration(String id, boolean approved) {
		User user = findById(id);
		user.setApprovedRegistration(approved);
		User updatedUser = repository.setApproveRegistration(user, approved);
		mqttStandardClientService.enable(new MqttStandardClient(updatedUser));
		return new UserDto(updatedUser);
	}
	
}
	
