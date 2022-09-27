package br.com.MyIot.dto.device;

import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDevice;

public class AnalogOutputDeviceDto extends DeviceDto {

	private Integer output;
	
	public AnalogOutputDeviceDto() {
		super();
	}
	
	public AnalogOutputDeviceDto(String id, String userId, String location, String name, Integer output) {
		super(id, userId, location, name);
		this.output = output;
	}
	
	public AnalogOutputDeviceDto(AnalogOutputDevice device) {
		super(device.getId(), device.getUser().getId(), device.getLocation(), device.getName());
		this.output = device.getOutput();
	}

	public Integer getOutput() {
		return output;
	}
	
	
}
