package br.com.MyIot.model.user;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String id;

	private Email email;

	private String name;

	private String password;

	private String clientMqttAccessKey;

	private boolean approvedRegistration;

	private List<Profile> profiles = new ArrayList<>();

	public User(String id, Email email, String name, String password, String clientMqttAccessKey,
			List<Profile> profiles) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.clientMqttAccessKey = clientMqttAccessKey;
		this.approvedRegistration = false;
		this.profiles = profiles;
	}

	public User(Email email, String name, String password, List<Profile> profiles) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.approvedRegistration = false;
		this.profiles = profiles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = new Email(email);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientMqttAccessKey() {
		return clientMqttAccessKey;
	}

	public void setClientMqttAccessKey(String clientMqttAccessKey) {
		this.clientMqttAccessKey = clientMqttAccessKey;
	}

	public boolean isApprovedRegistration() {
		return approvedRegistration;
	}

	public void setApprovedRegistration(boolean approvedRegistration) {
		this.approvedRegistration = approvedRegistration;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password
				+ ", clientMqttAccessKey=" + clientMqttAccessKey + ", approvedRegistration=" + approvedRegistration
				+ ", profiles=" + profiles + "]";
	}

}
