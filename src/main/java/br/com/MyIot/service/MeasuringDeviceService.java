package br.com.MyIot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.MeasuredValueDto;
import br.com.MyIot.dto.device.MeasuringDeviceDto;
import br.com.MyIot.dto.device.MeasuringDeviceForm;
import br.com.MyIot.exception.DataIntegratyViolationException;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.exception.OperationNotAllowedException;
import br.com.MyIot.exception.UserNotApprovedException;
import br.com.MyIot.model.device.MeasuringDevice.DateFilter;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValue;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValueRepository;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevicePerUser;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDeviceRepository;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
import br.com.MyIot.mqtt.MqttDeviceRoleService;

/**
 * A classe <b>MeasuringDeviceService</b> é uma classe service responsável pelo gerenciamento de dispositivos do tipo 
 * <b>MeasuringDevice</b>
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class MeasuringDeviceService {

	@Autowired
	private MeasuringDeviceRepository repository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MeasuredValueRepository measuredValueReporsitory;

	@Autowired
	private MqttDeviceRoleService mqttDeviceRoleService;

	@Autowired
	private MeasuringDevicePerUser devicePerUser;

	public String create(MeasuringDeviceForm form) {
		if(form.getKeyNames().size() > 4) {
			throw new DataIntegratyViolationException("Maximum keyNames number is 4!");
		}
		User autenticatedUser = getAuthenticatedUser();
		User user = userRepository.findById(autenticatedUser.getId()).get();
		if (!user.isApprovedRegistration()) {
			throw new UserNotApprovedException("User " + user.getName() + " not approved!");
		}
		Integer numberOfDevices = findAllByUser(20000).size();
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
		if(form.getKeyNames().size() > 4) {
			throw new DataIntegratyViolationException("Maximum keyNames number is 4!");
		}
		MeasuringDevice device = findById(id);
		device.setLocation(form.getLocation());
		device.setName(form.getName());
		device.setKeyNames(form.getKeyNames());
		return new MeasuringDeviceDto(repository.update(device));
	}

	public MeasuringDeviceDto findByIdDto(String id, Integer measurementLimit) {
		MeasuringDevice device = findById(id);
		List<MeasuredValue> measuredValues = measuredValueReporsitory.findAllByDevice(device, measurementLimit);
		List<MeasuredValueDto> measuredValuesDto = measuredValues.stream()
				.map(measuredValue -> new MeasuredValueDto(measuredValue))
				.toList();
		return new MeasuringDeviceDto(device, measuredValuesDto);
	}

	public MeasuringDevice findById(String id) {
		Optional<MeasuringDevice> device = repository.findById(id);
		if(device.isPresent() && !device.get().getUser().equals(getAuthenticatedUser())) {
			throw new OperationNotAllowedException("User without permission to execute this operation!");
		}
		return device
				.orElseThrow(() -> new ObjectNotFoundException("Device with id " + id + " not found in database!"));
	}

	public List<MeasuringDeviceDto> findAllByUser(Integer measurementLimit) {
		User user = getAuthenticatedUser();
		List<MeasuringDeviceDto> devicesDto = new ArrayList<>();
		List<MeasuringDevice> devices = repository.findAllByUser(user);
		Map<MeasuringDevice, List<MeasuredValue>> map = measuredValueReporsitory.findAllByDevices(devices, measurementLimit);
		map.forEach((device, measuredValues) -> {
			List<MeasuredValueDto> measuredValuesDto = measuredValues.stream()
							.map(measuredValue -> new MeasuredValueDto(measuredValue))
							.toList();
			devicesDto.add(new MeasuringDeviceDto(device, measuredValuesDto));
		});
		return devicesDto;
	}
	
	public List<MeasuringDeviceDto> findAllByUserAndTimeRange(DateFilter filter,Integer measurementLimit) {
		User user = getAuthenticatedUser();
		List<MeasuringDeviceDto> devicesDto = new ArrayList<>();
		List<MeasuringDevice> devices = repository.findAllByUser(user);
		Map<MeasuringDevice, List<MeasuredValue>> map = measuredValueReporsitory.findAllByDevicesAndTimeRange(devices, filter, measurementLimit);
		map.forEach((device, measuredValues) -> {
			List<MeasuredValueDto> measuredValuesDto = measuredValues.stream()
							.map(measuredValue -> new MeasuredValueDto(measuredValue))
							.toList();
			devicesDto.add(new MeasuringDeviceDto(device, measuredValuesDto));
		});
		return devicesDto;
	} 


	public List<MeasuringDeviceDto> findAll() {
		return repository.findAll().stream().map(device -> new MeasuringDeviceDto(device)).toList();
	}

	private User getAuthenticatedUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}