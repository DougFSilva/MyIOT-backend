package br.com.MyIot.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.MyIot.dto.user.UserDto;
import br.com.MyIot.dto.user.UserForm;
import br.com.MyIot.dto.user.UserUpdatePasswordForm;
import br.com.MyIot.exception.DataIntegratyViolationException;
import br.com.MyIot.exception.InvalidPasswordException;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDeviceRepository;
import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDeviceRepository;
import br.com.MyIot.model.device.discreteDevice.DiscreteDeviceRepository;
import br.com.MyIot.model.user.Email;
import br.com.MyIot.model.user.Profile;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
import br.com.MyIot.model.user.password.PasswordManager;
import br.com.MyIot.mqtt.MqttStandardClient;
import br.com.MyIot.mqtt.MqttStandardClientService;

@RestController
@RequestMapping
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
		List<Profile> profiles = Arrays.asList(new Profile(ProfileType.SILVER_USER));
		User user = new User(form.getEmail(), form.getName(), form.getPassword(), profiles);
		user.setPassword(password);
		user.setClientMqttPassword(UUID.randomUUID().toString());
		String createdUserId = repository.create(user);
		mqttStandardClientService.create(new MqttStandardClient(user));
		return createdUserId;
	};

	public void delete() {
		User user = getAuthenticatedUser();
		discreteDeviceRepository.deleteAllByUser(user);
		measuringDeviceRepository.deleteAllByUser(user);
		analogOutputDeviceRepository.deleteAllByUser(user);
		repository.delete(user);
		mqttStandardClientService.delete(new MqttStandardClient(user));
		return;
	}

	public UserDto update(UserForm form) {
		User user = getAuthenticatedUser();
		user.setName(form.getName());
		return new UserDto(repository.update(user));
	}

	public UserDto updatePasswordById(UserUpdatePasswordForm form) {
		User user = getAuthenticatedUser();
		if (form.getCurrentPassword().equals(form.getNewPassword())) {
			throw new InvalidPasswordException("The new password must be different from the current password!");
		}
		if (!passwordManager.compare(form.getCurrentPassword(), user.getPassword())) {
			throw new InvalidPasswordException("Incorrect current password!");
		}
		String password = passwordManager.validateAndEncode(form.getNewPassword());
		user.setPassword(password);
		return new UserDto(repository.update(user));
	}

	public UserDto findByIdDto() {
		return new UserDto(getAuthenticatedUser());
	}
	
	private User getAuthenticatedUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
