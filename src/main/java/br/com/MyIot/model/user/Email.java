package br.com.MyIot.model.user;

import br.com.MyIot.exception.InvalidEmailException;

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
