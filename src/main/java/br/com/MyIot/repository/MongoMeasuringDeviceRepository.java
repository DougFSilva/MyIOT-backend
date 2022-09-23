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
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import br.com.MyIot.dto.MeasuringDeviceDto;
import br.com.MyIot.model.device.MeasuringDevice;
import br.com.MyIot.model.device.MeasuringDeviceRepository;
import br.com.MyIot.model.user.User;
import br.com.MyIot.repository.codec.MeasuringDeviceCodec;
import br.com.MyIot.repository.config.MongoConnection;
import br.com.MyIot.repository.converter.MongoMeasuringDeviceConverter;
import br.com.MyIot.repository.entity.MeasuringDeviceDefaultCollectionName;
import br.com.MyIot.repository.entity.MongoMeasuringDeviceEntity;

@Repository
public class MongoMeasuringDeviceRepository implements MeasuringDeviceRepository {

	@Autowired
	private MongoConnection mongoConnection;

	@Autowired
	private MongoMeasuringDeviceConverter deviceConverter;

	@Autowired
	private MeasuringDeviceDefaultCollectionName collectionName;

	@Autowired
	private MongoUserRepository userRepository;

	@Override
	public String create(MeasuringDevice device) {
		MongoClient client = getClient();
		InsertOneResult result = getCollection(client).insertOne(deviceConverter.toEntity(device));
		client.close();
		createCollection(result.getInsertedId().asObjectId().getValue().toHexString());
		return result.getInsertedId().asObjectId().getValue().toHexString();
	}

	@Override
	public MeasuringDevice updateById(MeasuringDevice updatedDevice) {
		MongoClient client = getClient();
		MongoMeasuringDeviceEntity entity = getCollection(client).findOneAndUpdate(
				Filters.eq(new ObjectId(updatedDevice.getId())),
				Updates.combine(Updates.set("location", updatedDevice.getLocation()), 
								Updates.set("name", updatedDevice.getName()),
								Updates.set("keyNames", updatedDevice.getKeyNames())),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
		client.close();
		User user = findUser(entity.getUserId());
		return deviceConverter.toDevice(user, entity);
	}

	@Override
	public void deleteById(String id) {
		MongoClient client = getClient();
		getCollection(client).deleteOne(Filters.eq(new ObjectId(id)));
		client.close();
		deleteCollection(id);
		return;
	}

	@Override
	public void deleteAllByUser(User user) {
		MongoClient client = getClient();
		MongoCursor<MongoMeasuringDeviceEntity> mongoCursor = getCollection(client)
				.find(Filters.eq("userId", new ObjectId(user.getId()))).iterator();
		mongoCursor.forEachRemaining(device -> deleteCollection(device.getId().toHexString()));
		getCollection(client).deleteMany(Filters.eq("userId", new ObjectId(user.getId())));
		client.close();
		return;
	}

	@Override
	public Optional<MeasuringDevice> findById(String id) {
		MongoClient client = getClient();
		Optional<MongoMeasuringDeviceEntity> entity = Optional
				.ofNullable(getCollection(client).find(Filters.eq(new ObjectId(id))).first());
		client.close();
		if (entity.isPresent()) {
			User user = findUser(entity.get().getUserId());
			return Optional.of(deviceConverter.toDevice(user, entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<MeasuringDevice> findAllByUser(User user) {
		MongoClient client = getClient();
		List<MeasuringDevice> devices = new ArrayList<>();
		MongoCursor<MongoMeasuringDeviceEntity> mongoCursor = getCollection(client)
				.find(Filters.eq("userId", new ObjectId(user.getId()))).iterator();
		client.close();
		mongoCursor.forEachRemaining(cursor -> devices.add(deviceConverter.toDevice(user, cursor)));
		return devices;
	}

	@Override
	public List<MeasuringDeviceDto> findAll() {
		MongoClient client = getClient();
		List<MeasuringDeviceDto> devicesDTO = new ArrayList<>();
		MongoCursor<MongoMeasuringDeviceEntity> mongoCursor = getCollection(client).find().iterator();
		client.close();
		mongoCursor.forEachRemaining(cursor -> devicesDTO.add(deviceConverter.toDeviceDTO(cursor)));
		return devicesDTO;
	}

	@Override
	public Integer countDevicesByUser(User user) {
		MongoClient client = getClient();
		long count = getCollection(client).countDocuments(Filters.eq("userId", new ObjectId(user.getId())));
		client.close();
		return (int) count;

	}

	private void createCollection(String deviceId) {
		MongoClient client = mongoConnection.getSimpleClient();
		String collectionName = this.collectionName.getName(deviceId);
		mongoConnection.getDatabase(client)
			.createCollection(collectionName,new CreateCollectionOptions()
					.capped(true)
					.sizeInBytes(5000000)
					.maxDocuments(20000));
		mongoConnection.getDatabase(client).getCollection(collectionName).createIndex(Indexes.ascending("deviceId"));
		client.close();
	}

	private void deleteCollection(String deviceId) {
		MongoClient client = mongoConnection.getSimpleClient();
		String collectionName = this.collectionName.getName(deviceId);
		mongoConnection.getDatabase(client).getCollection(collectionName).drop();
		client.close();
	}

	private User findUser(ObjectId userId) {
		return userRepository.findById(userId.toHexString()).get();
	}

	private MongoClient getClient() {
		return mongoConnection.getClient(new MeasuringDeviceCodec());
	}

	private MongoCollection<MongoMeasuringDeviceEntity> getCollection(MongoClient client) {
		return mongoConnection.getDatabase(client).getCollection("measuringDevice", MongoMeasuringDeviceEntity.class);
	}

}
