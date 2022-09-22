package br.com.MyIot.model.user.password;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.MyIot.model.user.password.validation.PasswordValidation;
import br.com.MyIot.model.user.password.validation.ValidationAtLeastOneDigit;
import br.com.MyIot.model.user.password.validation.ValidationAtLeastOneLowerCase;
import br.com.MyIot.model.user.password.validation.ValidationAtLeastOneSpecialCharacter;
import br.com.MyIot.model.user.password.validation.ValidationAtLeastOneUpperCase;
import br.com.MyIot.model.user.password.validation.ValidationBlankSpace;
import br.com.MyIot.model.user.password.validation.ValidationPasswordSize;

public class PasswordManager {

	private String password;

	private final PasswordEncoder encoder;

	private List<PasswordValidation> validations = new ArrayList<>();

	public PasswordManager(String password, PasswordEncoder encoder) {
		this.password = password;
		this.encoder = encoder;
		this.validations = getValidation();
	}

	public String validateAndEncode() {
		this.validations.forEach(validation -> {
			validation.validate(this.password);
		});
		return this.encoder.encode(this.password);
	}

	private List<PasswordValidation> getValidation() {
		return Arrays.asList(new ValidationAtLeastOneDigit(), new ValidationAtLeastOneLowerCase(),
				new ValidationAtLeastOneSpecialCharacter(), new ValidationAtLeastOneUpperCase(),
				new ValidationBlankSpace(), new ValidationPasswordSize());
	}

}
