package br.com.MyIot.model.user;

public class PasswordValidator {
	
	private String password;
	
	private final PasswordEncoder encoder;

	public PasswordValidator(String password, PasswordEncoder encoder) {
		this.password = password;
		this.encoder = encoder;
	}
	
	public PasswordValidator validate() {
		
		return this;
	}
	
	private String encode() {
		
	}
}
