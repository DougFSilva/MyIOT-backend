package br.com.MyIot.model.user;

import java.util.List;
import java.util.Optional;

/**
 * A interface <b>UserRepository</b> define os métodos que a classe responsável pela persistência dos usuários deve implementar
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public interface UserRepository {

	String create(User user);

	void delete(User user);

	User update(User updatedUser);

	Optional<User> findById(String id);

	Optional<User> findByEmail(Email email);

	List<User> findAll();
	
	List<User> findUsersToAprrove();

	User setApproveRegistration(User user, boolean approved);
}
