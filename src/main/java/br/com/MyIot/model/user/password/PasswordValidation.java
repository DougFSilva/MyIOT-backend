package br.com.MyIot.model.user.password;

/**
 * A Interface <b>PasswordValidation</b> define o método a ser implementado pelas classes que fazem validação de password
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public interface PasswordValidation {

	void validate(String password);
}
