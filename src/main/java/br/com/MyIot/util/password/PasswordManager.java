package br.com.MyIot.util.password;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.MyIot.util.password.validation.PasswordValidation;
import br.com.MyIot.util.password.validation.ValidationAtLeastOneDigit;
import br.com.MyIot.util.password.validation.ValidationAtLeastOneLowerCase;
import br.com.MyIot.util.password.validation.ValidationAtLeastOneSpecialCharacter;
import br.com.MyIot.util.password.validation.ValidationAtLeastOneUpperCase;
import br.com.MyIot.util.password.validation.ValidationBlankSpace;
import br.com.MyIot.util.password.validation.ValidationPasswordSize;

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
