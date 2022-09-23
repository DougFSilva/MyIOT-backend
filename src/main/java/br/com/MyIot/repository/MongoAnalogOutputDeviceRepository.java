package br.com.MyIot.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import br.com.MyIot.model.device.AnalogOutputDevice;
import br.com.MyIot.model.device.AnalogOutputDeviceRepository;
import br.com.MyIot.model.user.User;
import br.com.MyIot.repository.codec.AnalogOutputDeviceCodec;
import br.com.MyIot.repository.config.MongoConnection;
import br.com.MyIot.repository.converter.MongoAnalogOutputDeviceConverter;
import br.com.MyIot.repository.entity.MongoAnalogOutputDeviceEntity;

@Repository
public class MongoAnalogOutputDeviceRepository implements AnalogOutputDeviceRepository {

	@Autowired
	private MongoConnection mongoConnection;

	@Autowired
	private MongoAnalogOutputDeviceConverter deviceConverter;

	@Autowired
	private MongoUserRepository userRepository;

	@Override
	public String create(AnalogOutputDevice device) {
		MongoClient client = getClient();
		InsertOneResult result = getCollection(client).insertOne(deviceConverter.toEntity(device));
		client.close();
		return result.getInsertedId().asObjectId().getValue().toHexString();
	}

	@Override
	public AnalogOutputDevice updateById(AnalogOutputDevice updatedDevice) {
		MongoClient client = getClient();
		MongoAnalogOutputDeviceEntity entity = getCollection(client).findOneAndUpdate(
				Filters.eq(new ObjectId(updatedDevice.getId())),
				Updates.combine(Updates.set("location", updatedDevice.getLocation()),
						Updates.set("name", updatedDevice.getName()), Updates.set("output", updatedDevice.getOutput())),
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
	public Optional<AnalogOutputDevice> findById(String id) {
		MongoClient client = getClient();
		Optional<MongoAnalogOutputDeviceEntity> entity = Optional
				.ofNullable(getCollection(client).find(Filters.eq(new ObjectId(id))).first());
		client.close();
		if (entity.isPresent()) {
			User user = userRepository.findById(entity.get().getUserId().toHexString()).get();
			return Optional.of(deviceConverter.toDevice(user, entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<AnalogOutputDevice> findAllByUser(User user) {
		MongoClient client = getClient();
		List<AnalogOutputDevice> devices = new ArrayList<>();
		MongoCursor<MongoAnalogOutputDeviceEntity> mongoCursor = getCollection(client)
				.find(Filters.eq("userId", new ObjectId(user.getId()))).iterator();
		client.close();
		mongoCursor.forEachRemaining(cursor -> devices.add(deviceConverter.toDevice(user, cursor)));
		return devices;
	}

	@Override
	public List<AnalogOutputDevice> findAll() {
		MongoClient client = getClient();
		List<AnalogOutputDevice> devices = new ArrayList<>();
		MongoCursor<MongoAnalogOutputDeviceEntity> mongoCursor = getCollection(client)
				.aggregate(Arrays.asList(Aggregates.lookup("user", "id", "userId", "user")))
				.iterator();
		mongoCursor.forEachRemaining(cursor -> devices.add(deviceConverter.toDevice(cursor)));
		return devices;
//		.iterator();
//		mongoCursor.forEachRemaining(cursor -> System.out.println(cursor));
//		MongoClient simpleClient = mongoConnection.getSimpleClient();
//		MongoCursor<Document> mongoCursor = simpleClient.getDatabase("iotProject")
//				.getCollection("analogOutputDevice")
//				.aggregate(Arrays.asList(Aggregates.lookup("user", "id", "userId", "user")), Document.class)
//		.iterator();
//		mongoCursor.forEachRemaining(cursor -> System.out.println(cursor));

		//return Arrays.asList(new AnalogOutputDevice());
	}

	private MongoClient getClient() {
		return mongoConnection.getClient(new AnalogOutputDeviceCodec());
	}

	private MongoCollection<MongoAnalogOutputDeviceEntity> getCollection(MongoClient client) {
		return mongoConnection.getDatabase(client).getCollection("analogOutputDevice",
				MongoAnalogOutputDeviceEntity.class);
	}

}
