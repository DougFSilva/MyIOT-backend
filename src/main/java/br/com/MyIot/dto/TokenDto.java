package br.com.MyIot.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.MyIot.model.user.Profile;

public class TokenDto {

	private String token;
	private String type;
	private String user;
	private List<String> profiles = new ArrayList<>();

	public TokenDto(String token, String type, String user, List<Profile> profiles) {
		this.token = token;
		this.type = type;
		this.user = user;
		profiles.forEach(profile -> this.profiles.add(profile.getType().toString()));
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}

	public String getUser() {
		return user;
	}

	public List<String> getProfiles() {
		return profiles;
	}

}
