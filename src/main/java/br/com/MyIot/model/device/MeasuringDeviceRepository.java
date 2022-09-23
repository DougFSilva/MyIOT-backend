package br.com.MyIot.model.device;

import java.util.List;
import java.util.Optional;

import br.com.MyIot.dto.MeasuringDeviceDto;
import br.com.MyIot.model.user.User;

public interface MeasuringDeviceRepository {

	String create(MeasuringDevice device);

	MeasuringDevice updateById(MeasuringDevice updatedDevice);

	void deleteById(String id);
	
	void deleteAllByUser(User user);

	Optional<MeasuringDevice> findById(String id);

	List<MeasuringDevice> findAllByUser(User user);

	List<MeasuringDeviceDto> findAll();
	
	Integer countDevicesByUser(User user);
	
}
