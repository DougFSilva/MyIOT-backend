package br.com.MyIot.dto.device;

import java.util.List;

import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.model.user.User;

public class MeasuringDeviceForm {

	protected String location;

	protected String name;

	private List<String> keyNames;

	public MeasuringDeviceForm(String location, String name, List<String> keyNames) {
		this.location = location;
		this.name = name;
		this.keyNames = keyNames;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public List<String> getKeyNames() {
		return keyNames;
	}
	
	public MeasuringDevice toDevice(User user) {
		return new MeasuringDevice(user, location, name, keyNames);
	}

}
