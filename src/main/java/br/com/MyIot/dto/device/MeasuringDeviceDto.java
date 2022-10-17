package br.com.MyIot.dto.device;

import java.util.ArrayList;
import java.util.List;

import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;

public class MeasuringDeviceDto extends DeviceDto {

	private List<String> keyNames = new ArrayList<>();

	private List<MeasuredValueDto> values = new ArrayList<>();

	public MeasuringDeviceDto() {
		super();
	}

	public MeasuringDeviceDto(String id, String userId, String location, String name, List<String> keyNames) {
		super(id, userId, location, name);
		this.keyNames = keyNames;
	}

	public MeasuringDeviceDto(MeasuringDevice device) {
		super(device.getId(), device.getUser().getId(), device.getLocation(), device.getName());
		this.keyNames = device.getKeyNames();
	}
	
	public MeasuringDeviceDto(MeasuringDevice device, List<MeasuredValueDto> values) {
		super(device.getId(), device.getUser().getId(), device.getLocation(), device.getName());
		this.keyNames = device.getKeyNames();
		this.values = values;
	}

	public List<String> getKeyNames() {
		return keyNames;
	}

	public List<MeasuredValueDto> getValues() {
		return values;
	}

}
