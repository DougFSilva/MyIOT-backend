package br.com.MyIot.repository.converter;

import org.springframework.stereotype.Service;

import br.com.MyIot.model.user.Profile;
import br.com.MyIot.repository.MongoProfile;

/**
 * A classe <b>MongoProfileConverter</b> é responsável pela conversão entre objetos do tipo <b>Profile</b> e <b>MongoProfileEntity</b>
 * @since Out 2022
 * @version 1.0
 */
@Service
public class MongoProfileConverter {

	public Profile toProfile(MongoProfile mongoProfile) {
		return new Profile(mongoProfile.getType());
	}
	
	public MongoProfile toMongoProfile(Profile profile) {
		return new MongoProfile(profile.getType());
	}
}
