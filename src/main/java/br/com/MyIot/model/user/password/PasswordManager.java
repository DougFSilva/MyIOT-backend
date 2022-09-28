package br.com.MyIot.model.user.password;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordManager {

	@Autowired
	private PasswordEncoder encoder;

	private List<PasswordValidation> validations = new ArrayList<>();

	public String validateAndEncode(String password) {
		this.validations = getValidation();
		this.validations.forEach(validation -> {
			validation.validate(password);
		});
		return encoder.encode(password);
	}

	public boolean compare(String rawPassword, String encodedPassword) {
		return encoder.matches(rawPassword, encodedPassword);
	}

	private List<PasswordValidation> getValidation() {
		return Arrays.asList(
				new ValidationAtLeastOneDigit(), 
				new ValidationAtLeastOneLowerCase(),
				new ValidationAtLeastOneSpecialCharacter(), 
				new ValidationAtLeastOneUpperCase(),
				new ValidationBlankSpace(),
				new ValidationPasswordSize());
	}
}
