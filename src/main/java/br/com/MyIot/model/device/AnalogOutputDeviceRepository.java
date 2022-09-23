package br.com.MyIot.model.device;

import java.util.List;
import java.util.Optional;

import br.com.MyIot.model.user.User;

public interface AnalogOutputDeviceRepository {

	String create(AnalogOutputDevice device);

	void deleteById(String id);

	void deleteAllByUser(User user);

	AnalogOutputDevice updateById(AnalogOutputDevice updatedDevice);

	Optional<AnalogOutputDevice> findById(String id);

	List<AnalogOutputDevice> findAllByUser(User user);

	List<AnalogOutputDevice> findAll();

}
