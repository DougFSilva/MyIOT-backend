package br.com.MyIot.model.user.password.validation;

import br.com.MyIot.exception.InvalidPasswordException;

public class ValidationAtLeastOneLowerCase implements PasswordValidation{

	@Override
	public void validate(String password) {
		if(!password.matches("^(?=.*[a-z]).*$")){
			throw new InvalidPasswordException("The password must contain at least one lowercase character");
		}
	}

}
