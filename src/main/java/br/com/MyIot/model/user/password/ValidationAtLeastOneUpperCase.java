package br.com.MyIot.model.user.password;

import br.com.MyIot.exception.InvalidPasswordException;

public class ValidationAtLeastOneUpperCase implements PasswordValidation{

	@Override
	public void validate(String password) {
		if(!password.matches("^(?=.*[A-Z]).*$")){
			throw new InvalidPasswordException("The password must contain at least one uppercase character");
		}
		
	}
}

