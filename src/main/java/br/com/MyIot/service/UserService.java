package br.com.MyIot.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.user.UserDto;
import br.com.MyIot.dto.user.UserForm;
import br.com.MyIot.dto.user.UserUpdatePasswordForm;
import br.com.MyIot.exception.DataIntegratyViolationException;
import br.com.MyIot.exception.InvalidPasswordException;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.model.device.AnalogOutputDeviceRepository;
import br.com.MyIot.model.device.DiscreteDeviceRepository;
import br.com.MyIot.model.device.MeasuringDeviceRepository;
import br.com.MyIot.model.user.Email;
import br.com.MyIot.model.user.Profile;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
import br.com.MyIot.mqtt.MqttStandardClient;
import br.com.MyIot.mqtt.MqttStandardClientService;
import br.com.MyIot.util.password.PasswordManager;

@Service
public class UserService {

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

	public String create(UserForm form) {
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
		repository.deleteById(id);
		mqttStandardClientService.delete(new MqttStandardClient(user));
		return;
	}

	public UserDto updateById(String id, UserForm form) {
		User user = findById(id);
		user.setName(form.getName());
		List<Profile> profiles = form.getProfiles().stream().map(profile -> new Profile(ProfileType.toEnum(profile)))
				.collect(Collectors.toList());
		user.setProfiles(profiles);
		return new UserDto(repository.updateById(user));
	}

	public void updatePasswordById(UserUpdatePasswordForm form) {
		User user = findById(form.getUserId());
		if(form.getCurrentPassword().equals(form.getNewPassword())) {
			throw new InvalidPasswordException("The new password must be different from the current password!");
		}
		if (!passwordManager.compare(form.getCurrentPassword(), user.getPassword())) {
			throw new InvalidPasswordException("Incorrect current password!");
		}
		String password = passwordManager.validateAndEncode(form.getNewPassword());
		user.setPassword(password);
		repository.updateById(user);
		return;
	}

	public UserDto findByIdDto(String id) {
		return new UserDto(findById(id));
	}

	public User findById(String id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found in database!"));
	}

	public UserDto findByEmail(String address) {
		Optional<User> user = repository.findByEmail(new Email(address));
		if (user.isEmpty()) {
			throw new ObjectNotFoundException("User with email " + address + " not found in database!");
		}
		return new UserDto(user.get());
	}

	public List<UserDto> findAll() {
		return repository.findAll().stream().map(user -> new UserDto(user)).toList();
	}

	public UserDto setApproveRegistration(String id, boolean approved) {
		User user = findById(id);
		user.setApprovedRegistration(approved);
		User updatedUser = repository.setApproveRegistration(user, approved);
		return new UserDto(updatedUser);
	}
}
