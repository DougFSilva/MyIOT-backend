package br.com.MyIot.dto.device;

import br.com.MyIot.model.device.discreteDevice.DiscreteDevice;
import br.com.MyIot.model.user.User;

public class DiscreteDeviceForm {

	private String location;

	private String name;

	public DiscreteDeviceForm(String location, String name) {
		this.location = location;
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}
	
	public DiscreteDevice toDevice(User user) {
		return new DiscreteDevice(user, location, name);
	}

}
