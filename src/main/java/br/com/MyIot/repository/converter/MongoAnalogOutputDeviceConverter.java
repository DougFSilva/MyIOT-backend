package br.com.MyIot.repository.converter;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.AnalogOutputDeviceDto;
import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDevice;
import br.com.MyIot.model.user.User;
import br.com.MyIot.repository.entity.MongoAnalogOutputDeviceEntity;

@Service
public class MongoAnalogOutputDeviceConverter {

	public AnalogOutputDevice toDevice(User user, MongoAnalogOutputDeviceEntity entity) {
		String id = entity.getId().toHexString();
		return new AnalogOutputDevice(id, user, entity.getLocation(), entity.getName(), entity.getOutput());
	}
	
	public AnalogOutputDeviceDto toDeviceDto(MongoAnalogOutputDeviceEntity entity) {
		String id = entity.getId().toHexString();
		String userId = entity.getUserId().toHexString();
		return new AnalogOutputDeviceDto(id, userId, entity.getLocation(), entity.getName(), entity.getOutput());
	}

	public MongoAnalogOutputDeviceEntity toEntity(AnalogOutputDevice device) {
		ObjectId id = new ObjectId();
		if (device.getId() != null) {
			id = new ObjectId(device.getId());
		}
		ObjectId userId = new ObjectId(device.getUser().getId());
		return new MongoAnalogOutputDeviceEntity(id, userId, device.getLocation(), device.getName(),
				device.getOutput());
	}

}
