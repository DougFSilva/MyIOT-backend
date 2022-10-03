package br.com.MyIot.repository.converter;

import org.bson.types.ObjectId;

import org.springframework.stereotype.Service;

import br.com.MyIot.model.device.MeasuringDevice.MeasuredValue;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.repository.entity.MongoMeasuredValueEntity;

/**
 * A classe <b>MongoMeasuredValueConverter</b> é responsável pela conversão
 * entre objetos do tipo <b>MeasuredValue</b> e <b>MongoMeasuredValueEntity</b>
 * @since Out 2022
 * @version 1.0
 */
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
