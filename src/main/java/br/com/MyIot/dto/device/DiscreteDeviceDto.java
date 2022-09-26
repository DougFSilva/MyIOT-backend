package br.com.MyIot.dto.device;

import br.com.MyIot.model.device.DiscreteDevice;

public class DiscreteDeviceDto extends DeviceDto {

private boolean status;
	
	public DiscreteDeviceDto() {
		super();
	}
	
	public DiscreteDeviceDto(String id, String userId, String location, String name, boolean status) {
		super(id, userId, location, name);
		this.status = status;
	}
	
	public DiscreteDeviceDto(DiscreteDevice device) {
		super(device.getId(), device.getUser().getId(), device.getLocation(), device.getName());
		this.status = device.isStatus();
	}

	public boolean isStatus() {
		return status;
	}

}
