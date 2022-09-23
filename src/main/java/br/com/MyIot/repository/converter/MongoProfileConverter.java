package br.com.MyIot.repository.converter;

import org.springframework.stereotype.Service;

import br.com.MyIot.model.user.Profile;
import br.com.MyIot.repository.MongoProfile;

@Service
public class MongoProfileConverter {

	public Profile toProfile(MongoProfile mongoProfile) {
		return new Profile(mongoProfile.getType());
	}
	
	public MongoProfile toMongoProfile(Profile profile) {
		return new MongoProfile(profile.getType());
	}
}
