package br.com.MyIot.model.user;

public class Profile {

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

}
