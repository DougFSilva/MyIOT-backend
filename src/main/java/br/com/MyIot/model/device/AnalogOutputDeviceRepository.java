package br.com.MyIot.model.device;

import java.util.List;
import java.util.Optional;

import br.com.MyIot.dto.AnalogOutputDeviceDto;
import br.com.MyIot.model.user.User;


public interface AnalogOutputDeviceRepository {

	String create(AnalogOutputDevice device);

	AnalogOutputDevice updateById(AnalogOutputDevice updatedDevice);

	void deleteById(String id);
	
	void deleteAllByUser(User user);

	Optional<AnalogOutputDevice> findById(String id);

	List<AnalogOutputDevice> findAllByUser(User user);

	List<AnalogOutputDeviceDto> findAll();
	
	Integer countDevicesByUser(User user);
}
