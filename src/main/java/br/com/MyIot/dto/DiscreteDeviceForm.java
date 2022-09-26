package br.com.MyIot.dto;

import br.com.MyIot.model.device.DiscreteDevice;
import br.com.MyIot.model.user.User;

public class DiscreteDeviceForm {

	protected String userId;

	protected String location;

	protected String name;

	private boolean status;

	public DiscreteDeviceForm(String userId, String location, String name, boolean status) {
		this.userId = userId;
		this.location = location;
		this.name = name;
		this.status = status;
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

	public boolean isStatus() {
		return status;
	}
	
	public DiscreteDevice toDevice(User user) {
		return new DiscreteDevice(user, location, name, status);
	}
	
}
