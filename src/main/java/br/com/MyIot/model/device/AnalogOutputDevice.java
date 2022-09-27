package br.com.MyIot.model.device;

import br.com.MyIot.model.user.User;

public class AnalogOutputDevice extends Device {

	private Integer output;

	public AnalogOutputDevice() {
		super();
	}

	public AnalogOutputDevice(String id, User user, String location, String name, Integer output) {
		super(id, user, location, name);
		this.output = output;
	}

	public AnalogOutputDevice(User user, String location, String name, Integer output) {
		super(user, location, name);
		this.output = output;
	}
	
	public AnalogOutputDevice(User user, String location, String name) {
		super(user, location, name);
		this.output = 0;
	}

	public Integer getOutput() {
		return output;
	}

	public void setOutput(Integer output) {
		this.output = output;
	}

}
