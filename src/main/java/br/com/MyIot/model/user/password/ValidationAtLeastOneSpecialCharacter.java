package br.com.MyIot.model.user.password;

import br.com.MyIot.exception.InvalidPasswordException;

public class ValidationAtLeastOneSpecialCharacter implements PasswordValidation {

	@Override
	public void validate(String password) {
		if(!password.matches("^(?=.*[@#$%^&-+=().]).*$")){
			throw new InvalidPasswordException("The password must contain at least one special character");
		}
		
	}

}
