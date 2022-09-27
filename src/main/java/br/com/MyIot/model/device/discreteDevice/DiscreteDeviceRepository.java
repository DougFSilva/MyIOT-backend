package br.com.MyIot.model.device.discreteDevice;

import java.util.List;
import java.util.Optional;

import br.com.MyIot.model.user.User;

public interface DiscreteDeviceRepository {

	String create(DiscreteDevice device);

	void delete(DiscreteDevice device);

	void deleteAllByUser(User user);

	DiscreteDevice update(DiscreteDevice updatedDevice);

	Optional<DiscreteDevice> findById(String id);

	List<DiscreteDevice> findAllByUser(User user);

	List<DiscreteDevice> findAll();

	Integer countDevicesByUser(User user);

}
