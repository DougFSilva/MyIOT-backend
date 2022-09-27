package br.com.MyIot.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import br.com.MyIot.model.device.MeasuringDevice;
import br.com.MyIot.model.device.MeasuringDeviceRepository;
import br.com.MyIot.model.user.Profile;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;
import br.com.MyIot.repository.codec.MeasuringDeviceCodec;
import br.com.MyIot.repository.config.MongoConnection;
import br.com.MyIot.repository.converter.MongoMeasuringDeviceConverter;
import br.com.MyIot.repository.entity.MongoMeasuringDeviceEntity;

@Repository
public class MongoMeasuringDeviceRepository implements MeasuringDeviceRepository {

	@Autowired
	private MongoConnection mongoConnection;

	@Autowired
	private MongoMeasuringDeviceConverter deviceConverter;

	@Autowired
	private MongoUserRepository userRepository;
	
	private final String collectionName = "measuringDevice";

	@Override
	public String create(MeasuringDevice device) {
		InsertOneResult result = getCollection().insertOne(deviceConverter.toEntity(device));
		mongoConnection.close();
		createCollection(result.getInsertedId().asObjectId().getValue().toHexString());
		return result.getInsertedId().asObjectId().getValue().toHexString();
	}

	@Override
	public MeasuringDevice update(MeasuringDevice updatedDevice) {
		MongoMeasuringDeviceEntity entity = getCollection().findOneAndUpdate(
				Filters.eq(new ObjectId(updatedDevice.getId())),
				Updates.combine(Updates.set("location", updatedDevice.getLocation()), 
								Updates.set("name", updatedDevice.getName()),
								Updates.set("keyNames", updatedDevice.getKeyNames())),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
		mongoConnection.close();
		User user = findUser(entity.getUserId());
		return deviceConverter.toDevice(user, entity);
	}

	@Override
	public void deleteById(String id) {
		getCollection().deleteOne(Filters.eq(new ObjectId(id)));
		mongoConnection.close();
		deleteCollection(id);
		return;
	}

	@Override
	public void deleteAllByUser(User user) {
		MongoCursor<MongoMeasuringDeviceEntity> mongoCursor = getCollection()
				.find(Filters.eq("userId", new ObjectId(user.getId()))).iterator();
		mongoCursor.forEachRemaining(device -> deleteCollection(device.getId().toHexString()));
		getCollection().deleteMany(Filters.eq("userId", new ObjectId(user.getId())));
		mongoConnection.close();
		return;
	}

	@Override
	public Optional<MeasuringDevice> findById(String id) {
		Optional<MongoMeasuringDeviceEntity> entity = Optional
				.ofNullable(getCollection().find(Filters.eq(new ObjectId(id))).first());
		mongoConnection.close();
		if (entity.isPresent()) {
			User user = findUser(entity.get().getUserId());
			return Optional.of(deviceConverter.toDevice(user, entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<MeasuringDevice> findAllByUser(User user) {
		List<MeasuringDevice> devices = new ArrayList<>();
		MongoCursor<MongoMeasuringDeviceEntity> mongoCursor = getCollection()
				.find(Filters.eq("userId", new ObjectId(user.getId()))).iterator();
		mongoConnection.close();
		mongoCursor.forEachRemaining(cursor -> devices.add(deviceConverter.toDevice(user, cursor)));
		return devices;
	}

	@Override
	public List<MeasuringDevice> findAll() {
		List<MeasuringDevice> devices = new ArrayList<>();
		MongoCursor<Document> mongoCursor = mongoConnection.connect().getDatabase().getCollection(collectionName, Document.class)
				.aggregate(Arrays.asList(Aggregates.lookup("user", "userId", "_id", "user"))).iterator();
		mongoCursor.forEachRemaining(cursor -> {
			Document document = cursor.getList("user", Document.class).get(0);
			List<Profile> profiles = document.getList("profiles", Document.class)
					.stream()
					.map(profileDocument -> new Profile(ProfileType.toEnum(profileDocument.getString("type"))))
					.toList();
			User user = new User(
					document.getObjectId("_id").toHexString(),
					document.getString("email"),
					document.getString("name"),
					document.getString("password"),
					document.getString("clientMqttPassword"),
					profiles
					);
			user.setApprovedRegistration(document.getBoolean("approvedRegistration"));
			MeasuringDevice device = new MeasuringDevice();
			device.setId(cursor.getObjectId("_id").toHexString());
			device.setLocation(cursor.getString("location"));
			device.setName(cursor.getString("name"));
			device.setKeyNames(cursor.getList("keyNames", String.class));
			device.setUser(user);
			devices.add(device);
		});
		return devices;
	}

	@Override
	public Integer countDevicesByUser(User user) {
		long count = getCollection().countDocuments(Filters.eq("userId", new ObjectId(user.getId())));
		mongoConnection.close();
		return (int) count;

	}

	private void createCollection(String collectionName) {
		mongoConnection.connect().getDatabase()
			.createCollection("device_" + collectionName,new CreateCollectionOptions()
					.capped(true)
					.sizeInBytes(5000000)
					.maxDocuments(20000));
		mongoConnection.getDatabase().getCollection("device_" + collectionName).createIndex(Indexes.ascending("deviceId"));
		mongoConnection.close();
	}

	private void deleteCollection(String collectionName) {
		mongoConnection.connect().getDatabase().getCollection("device_" + collectionName).drop();
		mongoConnection.close();
	}

	private User findUser(ObjectId userId) {
		return userRepository.findById(userId.toHexString()).get();
	}

	private MongoCollection<MongoMeasuringDeviceEntity> getCollection() {
		return mongoConnection.connect(new MeasuringDeviceCodec()).getDatabase().getCollection(collectionName,
				MongoMeasuringDeviceEntity.class);
	}

}
