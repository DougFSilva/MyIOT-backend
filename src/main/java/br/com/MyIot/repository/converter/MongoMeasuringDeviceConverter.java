package br.com.MyIot.repository.converter;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.MeasuringDeviceDto;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.model.user.User;
import br.com.MyIot.repository.entity.MongoMeasuringDeviceEntity;

/**
 * A classe <b>MongoMeasuringDeviceConverter</b> é responsável pela conversão entre objetos do tipo <b>MeasuringDevice</b>
 * e <b>MongoMeasuringDeviceEntity</b>
 * @since Out 2022
 * @version 1.0
 */
@Service
public class MongoMeasuringDeviceConverter {

	public MeasuringDevice toDevice(User user, MongoMeasuringDeviceEntity entity) {
		String id = entity.getId().toHexString();
		return new MeasuringDevice(id, user, entity.getLocation(), entity.getName(), entity.getKeyNames());
	}

	public MeasuringDeviceDto toDeviceDTO(MongoMeasuringDeviceEntity entity) {
		String id = entity.getId().toHexString();
		String userId = entity.getUserId().toHexString();
		return new MeasuringDeviceDto(id, userId, entity.getLocation(), entity.getName(), entity.getKeyNames());
	}

	public MongoMeasuringDeviceEntity toEntity(MeasuringDevice device) {
		return new MongoMeasuringDeviceEntity(new ObjectId(device.getUser().getId()), device.getLocation(),
				device.getName(), device.getKeyNames());
	}
}
