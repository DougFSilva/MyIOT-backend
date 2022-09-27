package br.com.MyIot.model.device;

import java.util.List;
import java.util.Optional;

import br.com.MyIot.model.user.User;

public interface MeasuringDeviceRepository {

	String create(MeasuringDevice device);

	void deleteById(String id);

	void deleteAllByUser(User user);

	MeasuringDevice update(MeasuringDevice updatedDevice);

	Optional<MeasuringDevice> findById(String id);

	List<MeasuringDevice> findAllByUser(User user);

	List<MeasuringDevice> findAll();

	Integer countDevicesByUser(User user);

}
