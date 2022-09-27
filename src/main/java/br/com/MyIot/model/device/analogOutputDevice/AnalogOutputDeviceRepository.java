package br.com.MyIot.model.device.analogOutputDevice;

import java.util.List;
import java.util.Optional;

import br.com.MyIot.model.user.User;

public interface AnalogOutputDeviceRepository {

	String create(AnalogOutputDevice device);

	void delete(AnalogOutputDevice device);

	void deleteAllByUser(User user);

	AnalogOutputDevice update(AnalogOutputDevice updatedDevice);

	Optional<AnalogOutputDevice> findById(String id);

	List<AnalogOutputDevice> findAllByUser(User user);

	List<AnalogOutputDevice> findAll();

}
