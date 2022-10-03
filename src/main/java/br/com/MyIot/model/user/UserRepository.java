package br.com.MyIot.model.user;

import java.util.List;
import java.util.Optional;

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
