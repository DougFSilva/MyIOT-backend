package br.com.MyIot.model.device.discreteDevice;
/**
 * A interface <b>DiscreteDeviceRepository</b> define os métodos que a classe responsável pela persistência dos dispositivos do tipo
 * <b>DiscreteDevice</b> deve implementar
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
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
