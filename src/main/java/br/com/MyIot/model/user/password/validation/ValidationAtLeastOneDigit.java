package br.com.MyIot.model.user.password.validation;

import br.com.MyIot.exception.InvalidPasswordException;

public class ValidationAtLeastOneDigit implements PasswordValidation {
	
	@Override
	public void validate(String password) {
		if(!password.matches("^(.*[0-9]).*$")){
			throw new InvalidPasswordException("The password must contain at least one digit");
		}
	}

}
