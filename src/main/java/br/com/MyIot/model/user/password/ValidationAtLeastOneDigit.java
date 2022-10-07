package br.com.MyIot.model.user.password;

import br.com.MyIot.exception.InvalidPasswordException;

/**
 * A classe <b>ValidationAtLeastOneDigit</b> faz a validação do password verificando se ele possui pelo menos um dígito
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class ValidationAtLeastOneDigit implements PasswordValidation {
	
	@Override
	public void validate(String password) {
		if(!password.matches("^(.*[0-9]).*$")){
			throw new InvalidPasswordException("The password must contain at least one digit");
		}
	}

}
