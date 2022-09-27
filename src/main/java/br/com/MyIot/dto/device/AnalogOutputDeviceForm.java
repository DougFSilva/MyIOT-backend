package br.com.MyIot.dto.device;

import br.com.MyIot.model.device.AnalogOutputDevice;
import br.com.MyIot.model.user.User;

public class AnalogOutputDeviceForm {
	
	private String userId;

	private String location;

	private String name;

	public AnalogOutputDeviceForm(String userId, String location, String name) {
		this.userId = userId;
		this.location = location;
		this.name = name;
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

	public AnalogOutputDevice toDevice(User user) {
		return new AnalogOutputDevice(user, this.getLocation(), this.getName());
	}

}
