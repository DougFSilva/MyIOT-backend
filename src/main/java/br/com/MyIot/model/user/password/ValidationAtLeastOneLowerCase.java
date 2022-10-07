package br.com.MyIot.model.user.password;

import br.com.MyIot.exception.InvalidPasswordException;

/**
 * A classe <b>ValidationAtLeastOneLowerCase</b> faz a validação do password verificando se ele possui pelo menos um 
 * caracter minúsculo
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class ValidationAtLeastOneLowerCase implements PasswordValidation{

	@Override
	public void validate(String password) {
		if(!password.matches("^(?=.*[a-z]).*$")){
			throw new InvalidPasswordException("The password must contain at least one lowercase character");
		}
	}

}
