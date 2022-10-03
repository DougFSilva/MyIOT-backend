package br.com.MyIot.model.device;

import br.com.MyIot.model.user.User;
/**
 * A Classe Abstrata <b>Device</b> define os parâmetros básicos de um dispositivo.
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public abstract class Device {

	protected String id;

	protected User user;

	protected String location;

	protected String name;

	protected Device() {
	}

	public Device(String id, User user, String location, String name) {
		this.id = id;
		this.user = user;
		this.location = location;
		this.name = name;
	}

	public Device(User user, String location, String name) {
		this.user = user;
		this.location = location;
		this.name = name;
	}

	public Device(String location, String name) {
		this.location = location;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", user=" + user + ", location=" + location + ", name=" + name + "]";
	}

}
