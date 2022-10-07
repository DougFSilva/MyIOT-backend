package br.com.MyIot.model.user.password;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A classe <b>PasswordManager</b> executa a validação de todas as validações definidas pelo método <i>getValidation</i> e 
 * realiza o encode o password
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class PasswordManager {

	@Autowired
	private PasswordEncoder encoder;

	private List<PasswordValidation> validations = new ArrayList<>();

	/**
	 * O método <b>validateAndEncode</b> recebe uma String password, executa as validações e faz o encode pelo 
	 * <b>PasswordEncoder</b>
	 * @param password
	 * @return Retorna uma String com o password validado e codificado
	 */
	public String validateAndEncode(String password) {
		this.validations = getValidation();
		this.validations.forEach(validation -> {
			validation.validate(password);
		});
		return encoder.encode(password);
	}

	/**
	 * O método <b>compare</b> recebe um password sem codificação e um password codificado e os compara
	 * @param rawPassword
	 * @param encodedPassword
	 * @return Retorna true se os passwords forem iguais e false caso sejam diferentes
	 */
	public boolean compare(String rawPassword, String encodedPassword) {
		return encoder.matches(rawPassword, encodedPassword);
	}

	/**
	 * O método <b>getValidation</b> define os objetos de validações desejados criados pelas classes que implementam a interface 
	 * <b>PasswordValidation</b>
	 * @return Retorna uma lista de <b>PasswordValidations</b>
	 */
	private List<PasswordValidation> getValidation() {
		return Arrays.asList(
				new ValidationAtLeastOneDigit(), 
				new ValidationAtLeastOneLowerCase(),
				new ValidationAtLeastOneSpecialCharacter(), 
				new ValidationAtLeastOneUpperCase(),
				new ValidationBlankSpace(),
				new ValidationPasswordSize());
	}
}
