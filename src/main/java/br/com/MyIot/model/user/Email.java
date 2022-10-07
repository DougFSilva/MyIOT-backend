package br.com.MyIot.model.user;

import br.com.MyIot.exception.InvalidEmailException;

/**
 * A classe <b>Email</b> define um email que é validado no momento da construção do objeto
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class Email {

	private String address;

	public Email(String address) {
		if (address == null || !address.matches("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {

			throw new InvalidEmailException("Email " + address + " is not valid!");
		}
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}
}
