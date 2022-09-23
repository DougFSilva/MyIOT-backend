package br.com.MyIot.model.device;

import java.util.List;
import java.util.Optional;

import br.com.MyIot.dto.DiscreteDeviceDto;
import br.com.MyIot.model.user.User;


public interface DiscreteDeviceRepository {

	String create(DiscreteDevice device);

	DiscreteDevice updateById(DiscreteDevice updatedDevice);

	void deleteById(String id);
	
	void deleteAllByUser(User user);

	Optional<DiscreteDevice> findById(String id);

	List<DiscreteDevice> findAllByUser(User user);

	List<DiscreteDeviceDto> findAll();
	
	Integer countDevicesByUser(User user);
	
	
}
