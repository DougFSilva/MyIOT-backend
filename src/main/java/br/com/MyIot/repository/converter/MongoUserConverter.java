package br.com.MyIot.repository.converter;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import br.com.MyIot.model.user.Profile;
import br.com.MyIot.model.user.User;
import br.com.MyIot.repository.MongoProfile;
import br.com.MyIot.repository.entity.MongoUserEntity;

@Service
public class MongoUserConverter {

	public User toUser(MongoUserEntity entity) {
		String id = entity.getId().toHexString();
		String email = entity.getEmail();
		String password = entity.getPassword();
		List<Profile> profiles = entity.getProfiles()
				.stream()
				.map(mongoProfile -> new MongoProfileConverter().toProfile(mongoProfile))
				.toList();
		User user = new User(email , entity.getName(), password, profiles);
		user.setId(id);
		user.setApprovedRegistration(entity.isApporvedRegistration());
		user.setClientMqttPassword(entity.getClientMqttPassword());
		return user;
	}

	public MongoUserEntity toMongoUserEntity(User user) {
		ObjectId id = new ObjectId();
		if (user.getId() != null) {
			id = new ObjectId(user.getId());
		}
		String email = user.getEmail().getAddress();
		String password = user.getPassword();
		List<MongoProfile> mongoProfiles = user.getProfiles()
				.stream()
				.map(profile -> new MongoProfileConverter().toMongoProfile(profile))
				.toList();
		MongoUserEntity mongoUserEntity = new MongoUserEntity(id, email, user.getName(), password,
				user.getClientMqttPassword(), user.isApprovedRegistration(), mongoProfiles);
		return mongoUserEntity;

	}
}
