package br.com.MyIot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.Document;
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

import br.com.MyIot.model.user.Email;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
import br.com.MyIot.repository.codec.UserCodec;
import br.com.MyIot.repository.config.MongoConnection;
import br.com.MyIot.repository.converter.MongoUserConverter;
import br.com.MyIot.repository.entity.MongoUserEntity;

@Repository
public class MongoUserRepository implements UserRepository {

	@Autowired
	private MongoConnection mongoConnection;

	@Autowired
	private MongoUserConverter mongoUserConverter;

	@Override
	public String create(User user) {
		MongoClient client = getClient();
		InsertOneResult result = getCollection(client).insertOne(mongoUserConverter.toMongoUserEntity(user));
		client.close();
		return result.getInsertedId().asObjectId().getValue().toHexString();
	}

	@Override
	public User updateById(User user) {
		MongoClient client = getClient();
		MongoUserEntity mongoUserEntity = mongoUserConverter.toMongoUserEntity(user);
		List<Document> profilesDocuments = mongoUserEntity.getProfiles()
				.stream()
				.map(profile -> new Document("type", profile.getType().getDescription()).append("authority",
						profile.getAuthority()))
				.collect(Collectors.toList());
		 MongoUserEntity entity = (getCollection(client).findOneAndUpdate(
				Filters.eq(new ObjectId(user.getId())),
				Updates.combine(Updates.set("email", mongoUserEntity.getEmail()),
								Updates.set("name", mongoUserEntity.getName()),
								Updates.set("password", user.getPassword()),
								Updates.set("approvedRegistration", mongoUserEntity.isApporvedRegistration()),
								Updates.set("profiles", profilesDocuments)),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)));
		client.close();
		return mongoUserConverter.toUser(entity);
	}

	@Override
	public void deleteById(String id) {
		MongoClient client = getClient();
		getCollection(client).deleteOne(Filters.eq(new ObjectId(id)));
		client.close();
		return;
	}

	@Override
	public Optional<User> findById(String id) {
		MongoClient client = getClient();
		Optional<MongoUserEntity> mongoUserEntity = Optional
				.ofNullable(getCollection(client).find(Filters.eq(new ObjectId(id))).first());
		client.close();
		return mongoUserEntity.isPresent() ? Optional.of(mongoUserConverter.toUser(mongoUserEntity.get()))
				: Optional.empty();
	}

	@Override
	public Optional<User> findByEmail(Email email) {
		MongoClient client = getClient();
		Optional<MongoUserEntity> mongoUserEntity = Optional
				.ofNullable(getCollection(client).find(Filters.eq("email", email.getAddress())).first());
		client.close();
		return mongoUserEntity.isPresent() ? Optional.of(mongoUserConverter.toUser(mongoUserEntity.get()))
				: Optional.empty();
	}

	@Override
	public List<User> findAll() {
		MongoClient client = getClient();
		List<User> users = new ArrayList<>();
		MongoCursor<MongoUserEntity> mongoCursor = getCollection(client).find().iterator();
		mongoCursor.forEachRemaining(cursor -> users.add(mongoUserConverter.toUser(cursor)));
		client.close();
		return users;
	}

	@Override
	public User setApproveRegistration(User user, boolean approved) {
		MongoClient client = getClient();
		getCollection(client).updateOne(Filters.eq(new ObjectId(user.getId())),
				Updates.set("approvedRegistration", approved));
		User updatedUser = findById(user.getId()).get();
		client.close();
		return updatedUser;
	}

	private MongoClient getClient() {
		return mongoConnection.getClient(new UserCodec());
	}

	private MongoCollection<MongoUserEntity> getCollection(MongoClient client) {
		return mongoConnection.getDatabase(client).getCollection("user", MongoUserEntity.class);
	}

}
