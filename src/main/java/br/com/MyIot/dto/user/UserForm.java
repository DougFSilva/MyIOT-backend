package br.com.MyIot.dto.user;

public class UserForm {

	private String email;

	private String name;

	private String password;

	public UserForm(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
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

	@Override
	public String toString() {
		return "UserForm [email=" + email + ", name=" + name + ", password=" + password + "]";
	}

}
