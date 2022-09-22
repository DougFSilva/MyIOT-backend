package br.com.MyIot.model.user;

public interface PasswordEncoder {

	String encode(String password);

	boolean compare(String currentPassword, String newPassword);

}
