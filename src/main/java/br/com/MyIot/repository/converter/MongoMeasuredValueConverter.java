package br.com.MyIot.repository.converter;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import br.com.MyIot.model.device.MeasuringDevice.MeasuredValue;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.repository.entity.MongoMeasuredValueEntity;
@Service
public class MongoMeasuredValueConverter {

	public MeasuredValue toMeasuredValue(MeasuringDevice device, MongoMeasuredValueEntity entity) {
		return new MeasuredValue(entity.getId().toHexString(), device, entity.getValues(), entity.getTimeStamp());
	}
	
	public MongoMeasuredValueEntity toEntity(MeasuredValue measuredValue) {
		ObjectId deviceId = new ObjectId(measuredValue.getDevice().getId());
		return new MongoMeasuredValueEntity(deviceId, measuredValue.getValues(), measuredValue.getTimestamp());
	}
}
