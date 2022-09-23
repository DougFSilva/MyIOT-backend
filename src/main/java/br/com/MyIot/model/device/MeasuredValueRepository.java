package br.com.MyIot.model.device;

import java.time.LocalDateTime;
import java.util.List;

public interface MeasuredValueRepository {

	String create(MeasuredValue measuredValue);
	
	void deleteById(MeasuringDevice device, String id);

	void deleteByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime, LocalDateTime finalDateTime);

	void deleteAllByDevice(MeasuringDevice device);

	List<MeasuredValue> findAllByDevice(MeasuringDevice device);

	List<MeasuredValue> findAllByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime, LocalDateTime finalDateTime);

}
