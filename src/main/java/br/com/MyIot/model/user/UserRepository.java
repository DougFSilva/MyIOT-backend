package br.com.MyIot.model.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

	String create(User user);

	void deleteById(String id);

	User updateById(User updatedUser);

	Optional<User> findById(String id);

	Optional<User> findByEmail(Email email);

	List<User> findAll();

	User setApproveRegistration(User user, boolean approved);
}
