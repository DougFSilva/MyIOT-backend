package br.com.MyIot.model.user.password;

import br.com.MyIot.exception.InvalidPasswordException;

/**
 * A classe <b>ValidationBlankSpace</b> faz a validação do password verificando se possui tem espaço em branco
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class ValidationBlankSpace implements PasswordValidation {

	@Override
	public void validate(String password) {
		if(!password.matches("^(?=\\S+$).*$")){
			throw new InvalidPasswordException("The password cannot contain blank spaces");
		}
		
	}

}
