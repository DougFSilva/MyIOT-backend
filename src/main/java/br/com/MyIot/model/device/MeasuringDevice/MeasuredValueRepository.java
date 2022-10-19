package br.com.MyIot.model.device.MeasuringDevice;

import java.util.List;
import java.util.Map;

/**
 * A interface <b>MeasuredValueRepository</b> define os métodos que a classe
 * responsável pela persistência dos valores medidos deve implementar
 * 
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public interface MeasuredValueRepository {

	String create(MeasuredValue measuredValue);

	void deleteById(MeasuringDevice device, String id);

	void deleteAllByDevice(MeasuringDevice device);

	void deleteByTimeRange(MeasuringDevice device, DateFilter filter);

	List<MeasuredValue> findAllByDevice(MeasuringDevice device, Integer limit);
	
	List<MeasuredValue> findAllByDeviceAndTimeRange(MeasuringDevice device, DateFilter filter, Integer limit);

	Map<MeasuringDevice, List<MeasuredValue>> findAllByDevices(List<MeasuringDevice> devices, Integer limit);

	Map<MeasuringDevice, List<MeasuredValue>> findAllByDevicesAndTimeRange(List<MeasuringDevice> devices, DateFilter filter, Integer limit);
	

}
