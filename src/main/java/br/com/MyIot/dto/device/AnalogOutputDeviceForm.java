package br.com.MyIot.dto.device;

import br.com.MyIot.model.device.AnalogOutputDevice;
import br.com.MyIot.model.user.User;

public class AnalogOutputDeviceForm {
	
	private String userId;

	private String location;

	private String name;

	private Integer output;

	public AnalogOutputDeviceForm(String userId, String location, String name, Integer output) {
		this.userId = userId;
		this.location = location;
		this.name = name;
		this.output = output;
	}
	
	public String getUserId() {
		return userId;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public Integer getOutput() {
		return output;
	}

	public AnalogOutputDevice toDevice(User user) {
		return new AnalogOutputDevice(user, this.getLocation(), this.getName(), this.getOutput());
	}

}
