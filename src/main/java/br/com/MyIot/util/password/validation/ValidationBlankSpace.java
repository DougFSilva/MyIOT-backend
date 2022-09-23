package br.com.MyIot.util.password.validation;

import br.com.MyIot.exception.InvalidPasswordException;

public class ValidationBlankSpace implements PasswordValidation {

	@Override
	public void validate(String password) {
		if(!password.matches("^(?=\\S+$).*$")){
			throw new InvalidPasswordException("The password cannot contain blank spaces");
		}
		
	}

}
