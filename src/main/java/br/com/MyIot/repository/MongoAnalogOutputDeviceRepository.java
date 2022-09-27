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
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;

import br.com.MyIot.model.device.AnalogOutputDevice;
import br.com.MyIot.model.device.AnalogOutputDeviceRepository;
import br.com.MyIot.model.user.Profile;
import br.com.MyIot.model.user.ProfileType;
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

	private final String collectionName = "analogOutputDevice";

	@Override
	public String create(AnalogOutputDevice device) {
		InsertOneResult result = getCollection().insertOne(deviceConverter.toEntity(device));
		mongoConnection.close();
		return result.getInsertedId().asObjectId().getValue().toHexString();
	}

	@Override
	public AnalogOutputDevice update(AnalogOutputDevice updatedDevice) {
		MongoAnalogOutputDeviceEntity entity = getCollection().findOneAndUpdate(
				Filters.eq(new ObjectId(updatedDevice.getId())),
				Updates.combine(Updates.set("location", updatedDevice.getLocation()),
						Updates.set("name", updatedDevice.getName()), Updates.set("output", updatedDevice.getOutput())),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
		mongoConnection.close();
		User user = userRepository.findById(entity.getUserId().toHexString()).get();
		return deviceConverter.toDevice(user, entity);
	}

	@Override
	public void deleteById(String id) {
		getCollection().deleteOne(Filters.eq(new ObjectId(id)));
		mongoConnection.close();
		return;
	}

	@Override
	public void deleteAllByUser(User user) {
		getCollection().deleteMany(Filters.eq("userId", new ObjectId(user.getId())));
		mongoConnection.close();
		return;
	}

	@Override
	public Optional<AnalogOutputDevice> findById(String id) {
		Optional<MongoAnalogOutputDeviceEntity> entity = Optional
				.ofNullable(getCollection().find(Filters.eq(new ObjectId(id))).first());
		mongoConnection.close();
		if (entity.isPresent()) {
			User user = userRepository.findById(entity.get().getUserId().toHexString()).get();
			return Optional.of(deviceConverter.toDevice(user, entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<AnalogOutputDevice> findAllByUser(User user) {
		List<AnalogOutputDevice> devices = new ArrayList<>();
		MongoCursor<MongoAnalogOutputDeviceEntity> mongoCursor = getCollection()
				.find(Filters.eq("userId", new ObjectId(user.getId()))).iterator();
		mongoConnection.close();
		mongoCursor.forEachRemaining(cursor -> devices.add(deviceConverter.toDevice(user, cursor)));
		return devices;
	}

	@Override
	public List<AnalogOutputDevice> findAll() {
		List<AnalogOutputDevice> devices = new ArrayList<>();
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
			AnalogOutputDevice device = new AnalogOutputDevice();
			device.setId(cursor.getObjectId("_id").toHexString());
			device.setLocation(cursor.getString("location"));
			device.setName(cursor.getString("name"));
			device.setOutput(cursor.getInteger("output"));
			device.setUser(user);
			devices.add(device);
		});
		return devices;
	}

	private MongoCollection<MongoAnalogOutputDeviceEntity> getCollection() {
		return mongoConnection.connect(new AnalogOutputDeviceCodec()).getDatabase().getCollection(collectionName,
				MongoAnalogOutputDeviceEntity.class);
	}

}
