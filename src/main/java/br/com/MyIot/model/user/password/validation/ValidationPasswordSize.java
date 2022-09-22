package br.com.MyIot.model.user.password.validation;

import br.com.MyIot.exception.InvalidPasswordException;

public class ValidationPasswordSize implements PasswordValidation {

	@Override
	public void validate(String password) {
		if(password.length()<8 || password.length()>20){
			throw new InvalidPasswordException("The password must contain between 8 and 20 characters");
		}
		
	}

}
