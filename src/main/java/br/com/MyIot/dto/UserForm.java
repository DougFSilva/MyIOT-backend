package br.com.MyIot.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.MyIot.model.user.Profile;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;

public class UserForm {

	private String email;

	private String name;

	private String password;

	private List<String> profiles = new ArrayList<>();

	public UserForm(String email, String name, String password, List<String> profiles) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.profiles = profiles;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getProfiles() {
		return profiles;
	}

	public User toUser() {
		List<Profile> profiles = this.profiles.stream().map(profile -> new Profile(ProfileType.toEnum(profile)))
				.collect(Collectors.toList());
		return new User(email, name, password, profiles);
	}

	@Override
	public String toString() {
		return "UserFORM [email=" + email + ", name=" + name + ", password=" + password + ", profiles=" + profiles
				+ "]";
	}

}
