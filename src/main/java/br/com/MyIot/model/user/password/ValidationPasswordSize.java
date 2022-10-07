package br.com.MyIot.model.user.password;

import br.com.MyIot.exception.InvalidPasswordException;

/**
 * A classe <b>ValidationPasswordSize</b> faz a validação do password verificando se ele possui a quantidade de caractéres entre 
 * um valor mínimo e máximo
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class ValidationPasswordSize implements PasswordValidation {

	@Override
	public void validate(String password) {
		if(password.length()<8 || password.length()>20){
			throw new InvalidPasswordException("The password must contain between 8 and 20 characters");
		}
		
	}

}
