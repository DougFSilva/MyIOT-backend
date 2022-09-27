package br.com.MyIot.repository.converter;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import br.com.MyIot.dto.device.DiscreteDeviceDto;
import br.com.MyIot.model.device.discreteDevice.DiscreteDevice;
import br.com.MyIot.model.user.User;
import br.com.MyIot.repository.entity.MongoDiscreteDeviceEntity;

@Service
public class MongoDiscreteDeviceConverter {

	public DiscreteDevice toDevice(User user, MongoDiscreteDeviceEntity entity) {
		String id = entity.getId().toHexString();
		return new DiscreteDevice(id, user, entity.getLocation(), entity.getName(), entity.isStatus());
	}

	public DiscreteDeviceDto toDeviceDTO(MongoDiscreteDeviceEntity entity) {
		String id = entity.getId().toHexString();
		String userId = entity.getUserId().toHexString();
		return new DiscreteDeviceDto(id, userId, entity.getLocation(), entity.getName(), entity.isStatus());
	}

	public MongoDiscreteDeviceEntity toEntity(DiscreteDevice device) {
		ObjectId id = new ObjectId();
		if (device.getId() != null) {
			id = new ObjectId(device.getId());
		}
		ObjectId userId = new ObjectId(device.getUser().getId());
		return new MongoDiscreteDeviceEntity(id, userId, device.getLocation(), device.getName(), device.isStatus());
	}

}
