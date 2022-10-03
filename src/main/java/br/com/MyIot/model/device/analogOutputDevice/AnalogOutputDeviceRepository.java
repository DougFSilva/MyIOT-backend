package br.com.MyIot.model.device.analogOutputDevice;

/**
 * A interface <b>AnalogOutputDeviceRepository</b> define os métodos que a classe responsável pela persistência dos dispositivos do tipo
 * <b>AnalogOutputDevice</b> deve implementar
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
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
