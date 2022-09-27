package br.com.MyIot.model.device.MeasuringDevice;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasuredValueRepository {

	String create(MeasuredValue measuredValue);
	
	void deleteById(MeasuringDevice device, String id);

	void deleteAllByDevice(MeasuringDevice device);
	
	void deleteByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime, LocalDateTime finalDateTime);

	List<MeasuredValue> findAllByDevice(MeasuringDevice device);

	List<MeasuredValue> findAllByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime, LocalDateTime finalDateTime);

}
