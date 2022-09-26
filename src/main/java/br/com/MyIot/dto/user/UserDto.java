package br.com.MyIot.dto.user;

import java.util.ArrayList;
import java.util.List;

import br.com.MyIot.model.user.Profile;
import br.com.MyIot.model.user.User;

public class UserDto {

	private String id;

	private String email;

	private String name;

	private String clientMqttPassword;

	private boolean approvedRegistration;

	private List<Profile> profiles = new ArrayList<>();

	public UserDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail().getAddress();
		this.name = user.getName();
		this.approvedRegistration = user.isApprovedRegistration();
		this.profiles = user.getProfiles();
		this.clientMqttPassword = user.getClientMqttPassword();
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getClientMqttPassword() {
		return clientMqttPassword;
	}

	public boolean isApprovedRegistration() {
		return approvedRegistration;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", email=" + email + ", name=" + name + ", clientMqttPassword="
				+ clientMqttPassword + ", approvedRegistration=" + approvedRegistration + ", profiles=" + profiles
				+ "]";
	}

}
