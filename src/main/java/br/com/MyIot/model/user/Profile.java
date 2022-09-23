package br.com.MyIot.model.user;

import org.springframework.security.core.GrantedAuthority;

public class Profile implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	private ProfileType type;

	public Profile() {
		super();
	}

	public Profile(ProfileType type) {
		this.type = type;
	}

	public ProfileType getType() {
		return type;
	}

	public void setType(ProfileType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Profile [type=" + type + "]";
	}

	@Override
	public String getAuthority() {
		return type.getDescription();
	}

}