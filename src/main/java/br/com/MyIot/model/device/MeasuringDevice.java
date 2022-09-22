package br.com.MyIot.model.device;

import java.util.ArrayList;
import java.util.List;

import br.com.MyIot.model.user.User;

public class MeasuringDevice extends Device {

	private List<String> keyNames = new ArrayList<>();

	public MeasuringDevice() {
		super();
	}

	public MeasuringDevice(String id, User user, String location, String name, List<String> keyNames) {
		super(id, user, location, name);
		this.keyNames = keyNames;
	}

	public MeasuringDevice(User user, String location, String name, List<String> keyNames) {
		super(user, location, name);
		this.keyNames = keyNames;
	}

	public MeasuringDevice(String location, String name, List<String> keyNames) {
		super(location, name);
		this.keyNames = keyNames;
	}

	public List<String> getKeyNames() {
		return keyNames;
	}

	public void setKeyNames(List<String> keyNames) {
		this.keyNames = keyNames;
	}

}
