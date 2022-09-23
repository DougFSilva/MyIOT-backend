package br.com.MyIot.mqtt;

import br.com.MyIot.model.user.User;

public class MqttStandardClient {

	private String id;

	private String username;

	private String password;

	public MqttStandardClient(User user) {
		this.id = user.getId();
		this.username = user.getEmail().getAddress();
		this.password = user.getClientMqttPassword();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
