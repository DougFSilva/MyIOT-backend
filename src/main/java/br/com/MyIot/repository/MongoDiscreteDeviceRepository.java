package br.com.MyIot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import br.com.MyIot.dto.DiscreteDeviceDto;
import br.com.MyIot.model.device.DiscreteDevice;
import br.com.MyIot.model.device.DiscreteDeviceRepository;
import br.com.MyIot.model.user.User;
import br.com.MyIot.repository.codec.DiscreteDeviceCodec;
import br.com.MyIot.repository.config.MongoConnection;
import br.com.MyIot.repository.converter.MongoDiscreteDeviceConverter;
import br.com.MyIot.repository.entity.MongoDiscreteDeviceEntity;

@Repository
public class MongoDiscreteDeviceRepository implements DiscreteDeviceRepository {

	@Autowired
	private MongoConnection mongoConnection;

	@Autowired
	private MongoDiscreteDeviceConverter deviceConverter;

	@Autowired
	private MongoUserRepository userRepository;

	@Override
	public String create(DiscreteDevice device) {
		MongoClient client = getClient();
		InsertOneResult result = getCollection(client).insertOne(deviceConverter.toEntity(device));
		client.close();
		return result.getInsertedId().asObjectId().getValue().toHexString();
	}

	@Override
	public DiscreteDevice updateById(DiscreteDevice updatedDevice) {
		MongoClient client = getClient();
		MongoDiscreteDeviceEntity entity = getCollection(client).findOneAndUpdate(
				Filters.eq(new ObjectId(updatedDevice.getId())),
				Updates.combine(Updates.set("location", updatedDevice.getLocation()),
						        Updates.set("name", updatedDevice.getName()), 
						        Updates.set("status", updatedDevice.isStatus())),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
		client.close();
		User user = userRepository.findById(entity.getUserId().toHexString()).get();
		return deviceConverter.toDevice(user, entity);
	}

	@Override
	public void deleteById(String id) {
		MongoClient client = getClient();
		getCollection(client).deleteOne(Filters.eq(new ObjectId(id)));
		client.close();
		return;
	}

	@Override
	public void deleteAllByUser(User user) {
		MongoClient client = getClient();
		getCollection(client).deleteMany(Filters.eq("userId", new ObjectId(user.getId())));
		client.close();
		return;
	}

	@Override
	public Optional<DiscreteDevice> findById(String id) {
		MongoClient client = getClient();
		Optional<MongoDiscreteDeviceEntity> entity = Optional
				.ofNullable(getCollection(client).find(Filters.eq(new ObjectId(id))).first());
		client.close();
		if (entity.isPresent()) {
			User user = userRepository.findById(entity.get().getUserId().toHexString()).get();
			return Optional.of(deviceConverter.toDevice(user, entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<DiscreteDevice> findAllByUser(User user) {
		MongoClient client = getClient();
		List<DiscreteDevice> devices = new ArrayList<>();
		MongoCursor<MongoDiscreteDeviceEntity> mongoCursor = getCollection(client)
				.find(Filters.eq("userId", new ObjectId(user.getId()))).iterator();
		client.close();
		mongoCursor.forEachRemaining(cursor -> devices.add(deviceConverter.toDevice(user, cursor)));
		return devices;
	}

	@Override
	public List<DiscreteDeviceDto> findAll() {
		MongoClient client = getClient();
		List<DiscreteDeviceDto> devicesDTO = new ArrayList<>();
		MongoCursor<MongoDiscreteDeviceEntity> mongoCursor = getCollection(client).find().iterator();
		client.close();
		mongoCursor.forEachRemaining(cursor -> {
			devicesDTO.add(deviceConverter.toDeviceDTO(cursor));
		});
		return devicesDTO;
	}

	@Override
	public Integer countDevicesByUser(User user) {
		MongoClient client = getClient();
		long count = getCollection(client).countDocuments(Filters.eq("userId", new ObjectId(user.getId())));
		client.close();
		return (int) count;
	}

	private MongoClient getClient() {
		return mongoConnection.getClient(new DiscreteDeviceCodec());
	}

	private MongoCollection<MongoDiscreteDeviceEntity> getCollection(MongoClient client) {
		return mongoConnection.getDatabase(client).getCollection("discreteDevice", MongoDiscreteDeviceEntity.class);
	}

}
