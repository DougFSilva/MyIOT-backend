package br.com.MyIot.model.device.discreteDevice;

import br.com.MyIot.model.device.Device;
import br.com.MyIot.model.user.User;

public class DiscreteDevice extends Device {

	private boolean status;
	
	public DiscreteDevice() {
		super();
	}

	public DiscreteDevice(String id, User user, String location, String name, boolean status) {
		super(id, user, location, name);
		this.status = status;
	}

	public DiscreteDevice(User user, String location, String name) {
		super(user, location, name);
		this.status = false;
	}
	
	public DiscreteDevice(String location, String name, boolean status) {
		super(location, name);
		this.status = status;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
