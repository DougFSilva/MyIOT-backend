package br.com.MyIot.repository;

import org.springframework.security.core.GrantedAuthority;

import br.com.MyIot.model.user.ProfileType;

/**
 * A classe <b>MongoProfile</b> define um tipo de perfil para a classe <b>MongoUserEntity</b>
 * @since Out 2022
 * @version 1.0
 */
public class MongoProfile implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	private ProfileType type;

	public MongoProfile() {
		super();
	}

	public MongoProfile(ProfileType type) {
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
