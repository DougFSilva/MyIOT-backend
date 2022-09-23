package br.com.MyIot.repository.converter;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.MeasuringDeviceDto;
import br.com.MyIot.model.device.MeasuringDevice;
import br.com.MyIot.model.user.User;
import br.com.MyIot.repository.entity.MongoMeasuringDeviceEntity;

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
