package br.com.MyIot.model.user.password;

import br.com.MyIot.exception.InvalidPasswordException;

/**
 * A classe <b>ValidationAtLeastOneUpperCase</b> faz a validação do password verificando se ele possui pelo menos um 
 * caracter maiúsculo
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class ValidationAtLeastOneUpperCase implements PasswordValidation{

	@Override
	public void validate(String password) {
		if(!password.matches("^(?=.*[A-Z]).*$")){
			throw new InvalidPasswordException("The password must contain at least one uppercase character");
		}
		
	}
}

