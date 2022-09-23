package br.com.MyIot.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.MyIot.model.device.MeasuringDevice;

public class MeasuringDeviceDto extends DeviceDto {

	private List<String> keyNames = new ArrayList<>();

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

	public List<String> getKeyNames() {
		return keyNames;
	}

}
