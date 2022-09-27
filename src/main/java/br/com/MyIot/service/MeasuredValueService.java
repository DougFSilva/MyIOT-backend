package br.com.MyIot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.MeasuredValueDto;
import br.com.MyIot.dto.device.MeasuredValueForm;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValue;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValueRepository;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;

@Service
public class MeasuredValueService {

	@Autowired
	private MeasuredValueRepository repository;

	@Autowired
	private MeasuringDeviceService measuringDeviceService;

	public String create(MeasuredValueForm form) {
		MeasuringDevice device = measuringDeviceService.findById(form.getDeviceId());
		return repository.create(new MeasuredValue(device, form.getValues(), form.getTimestamp()));
	}

	public void deleteById(String deviceId, String id) {
		MeasuringDevice device = measuringDeviceService.findById(deviceId);
		repository.deleteById(device, id);
		return;
	}

	public void deleteAllByDevice(String deviceId) {
		MeasuringDevice device = measuringDeviceService.findById(deviceId);
		repository.deleteAllByDevice(device);
		return;
	}

	public void deleteByTimeRange(String deviceId, LocalDateTime initialDateTime, LocalDateTime finalDateTime) {
		MeasuringDevice device = measuringDeviceService.findById(deviceId);
		repository.deleteByTimeRange(device, initialDateTime, finalDateTime);
		return;
	}

	public List<MeasuredValueDto> findAllByDevice(String deviceId) {
		MeasuringDevice device = measuringDeviceService.findById(deviceId);
		return repository.findAllByDevice(device)
				.stream()
				.map(measuredValue -> new MeasuredValueDto(measuredValue))
				.toList();
	}

	public List<MeasuredValueDto> findAllByTimeRange(String deviceId, LocalDateTime initialDateTime,
			LocalDateTime finalDateTime) {
		MeasuringDevice device = measuringDeviceService.findById(deviceId);
		return repository.findAllByTimeRange(device, initialDateTime, finalDateTime)
				.stream()
				.map(measuredValue -> new MeasuredValueDto(measuredValue))
				.toList();
	}
}
