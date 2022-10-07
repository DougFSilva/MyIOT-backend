package br.com.MyIot.model.user.password;

import br.com.MyIot.exception.InvalidPasswordException;

/**
 * A classe <b>ValidationAtLeastOneSpecialCharacter</b> faz a validação do password verificando se ele possui pelo menos um 
 * caracter especial
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class ValidationAtLeastOneSpecialCharacter implements PasswordValidation {

	@Override
	public void validate(String password) {
		if(!password.matches("^(?=.*[@#$%^&-+=().]).*$")){
			throw new InvalidPasswordException("The password must contain at least one special character");
		}
		
	}

}
