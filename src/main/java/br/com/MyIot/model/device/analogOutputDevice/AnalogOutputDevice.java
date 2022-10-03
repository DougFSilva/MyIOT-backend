package br.com.MyIot.model.device.analogOutputDevice;

import br.com.MyIot.model.device.Device;

import br.com.MyIot.model.user.User;
/**
 * A classe <b>AnalogOutputDevice</b> define um dispositivo a ser comandado por sinal analógico.
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
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
