package br.com.MyIot.model.device.MeasuringDevice;

import java.time.LocalDateTime;
import java.util.List;
/**
 * A interface <b>MeasuredValueRepository</b> define os métodos que a classe responsável pela persistência dos valores medidos deve implementar
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public interface MeasuredValueRepository {

	String create(MeasuredValue measuredValue);
	
	void deleteById(MeasuringDevice device, String id);

	void deleteAllByDevice(MeasuringDevice device);
	
	void deleteByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime, LocalDateTime finalDateTime);

	List<MeasuredValue> findAllByDevice(MeasuringDevice device);

	List<MeasuredValue> findAllByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime, LocalDateTime finalDateTime);

}
