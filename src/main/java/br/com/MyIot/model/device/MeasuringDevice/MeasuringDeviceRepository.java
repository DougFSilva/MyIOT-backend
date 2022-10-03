package br.com.MyIot.model.device.MeasuringDevice;

import java.util.List;
import java.util.Optional;

import br.com.MyIot.model.user.User;

/**
 * A interface <b>MeasuringDeviceRepository</b> define os métodos que a classe responsável pela persistência dos dispositivos do tipo
 * <b>MeasuringDevice</b> deve implementar
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public interface MeasuringDeviceRepository {

	String create(MeasuringDevice device);

	void delete(MeasuringDevice device);
	
	MeasuringDevice update(MeasuringDevice updatedDevice);

	void deleteAllByUser(User user);

	Optional<MeasuringDevice> findById(String id);

	List<MeasuringDevice> findAllByUser(User user);

	List<MeasuringDevice> findAll();

	Integer countDevicesByUser(User user);

}
