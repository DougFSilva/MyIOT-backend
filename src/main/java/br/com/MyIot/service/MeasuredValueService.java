package br.com.MyIot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.MeasuredValueDto;
import br.com.MyIot.dto.device.MeasuredValueForm;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.exception.OperationNotAllowedException;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValue;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValueRepository;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDeviceRepository;
import br.com.MyIot.model.user.User;

/**
 * A classe <b>MeasuredValueService</b> é uma classe service responsável pelo gerenciamento valores de medição do tipo 
 * <b>MeasuredValue</b>
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class MeasuredValueService {

	@Autowired
	private MeasuredValueRepository repository;

	@Autowired
	private MeasuringDeviceRepository measuringDeviceRepository;
	
	@Autowired
	private WebSocketMessager messager;

	public String create(MeasuredValueForm form) {
		MeasuringDevice device = findDeviceById(form.getDeviceId());
		return repository.create(new MeasuredValue(device, form.getValues(), form.getTimestamp()));
	}
	
	public void mqttCreate(MeasuredValueForm form) {
		Optional<MeasuringDevice> device = measuringDeviceRepository.findById(form.getDeviceId());
		if(device.isPresent()) {
			MeasuredValue measuredValue = new MeasuredValue(device.get(), form.getValues(), form.getTimestamp());
			String createMeasuredValueId = repository.create(measuredValue);
			measuredValue.setId(createMeasuredValueId);
			messager.sendMessage(measuredValue);
		}
	}

	public void deleteById(String deviceId, String id) {
		MeasuringDevice device = findDeviceById(deviceId);
		repository.deleteById(device, id);
		return;
	}

	public void deleteAllByDevice(String deviceId) {
		MeasuringDevice device = findDeviceById(deviceId);
		repository.deleteAllByDevice(device);
		return;
	}

	public void deleteByTimeRange(String deviceId, LocalDateTime initialDateTime, LocalDateTime finalDateTime) {
		MeasuringDevice device = findDeviceById(deviceId);
		repository.deleteByTimeRange(device, initialDateTime, finalDateTime);
		return;
	}

	public List<MeasuredValueDto> findAllByDevice(String deviceId) {
		MeasuringDevice device = findDeviceById(deviceId);
		return repository.findAllByDevice(device)
				.stream()
				.map(measuredValue -> new MeasuredValueDto(measuredValue))
				.toList();
	}

	public List<MeasuredValueDto> findAllByTimeRange(String deviceId, LocalDateTime initialDateTime,
			LocalDateTime finalDateTime) {
		MeasuringDevice device = findDeviceById(deviceId);
		return repository.findAllByTimeRange(device, initialDateTime, finalDateTime)
				.stream()
				.map(measuredValue -> new MeasuredValueDto(measuredValue))
				.toList();
	}
	
	private MeasuringDevice findDeviceById(String deviceId) {
		Optional<MeasuringDevice> device = measuringDeviceRepository.findById(deviceId);
		if(device.isPresent() && !device.get().getUser().equals(getAuthenticatedUser())) {
			throw new OperationNotAllowedException("User without permission to execute this operation!");
		}
		return device.orElseThrow(() -> new ObjectNotFoundException("Device with id " + deviceId + " not found in database!"));
	}
	
	private User getAuthenticatedUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}