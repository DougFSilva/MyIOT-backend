package br.com.MyIot.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Indexes;

import br.com.MyIot.model.user.Email;
import br.com.MyIot.model.user.Profile;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
import br.com.MyIot.mqtt.MqttSystemClientSubscriber;
import br.com.MyIot.repository.config.MongoConnection;

@Configuration
public class InitialConfiguration implements ApplicationRunner {
	
	@Value("${mqtt.uri}")
	private String mqttUri;

	@Value("${user.master.email}")
	private String userMasterEmail;

	@Value("${user.master.name}")
	private String userMasterName;

	@Value("${user.master.password}")
	private String userMasterPassword;

	@Value("${user.master.mqttPassword}")
	private String userMasterMqttPassword;

	@Value("${user.master.mqttClientId}")
	private String userMasterMqttClientId;
	
	@Autowired
	private MqttSystemClientSubscriber mqttClient;
	
	@Autowired
	private MongoConnection mongoConnection;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if(userRepository.findByEmail(new Email(userMasterEmail)).isEmpty()) {
			List<Profile> profiles = Arrays.asList(new Profile(ProfileType.ADMIN));
			User user = new User(userMasterEmail, userMasterName, userMasterPassword, userMasterMqttPassword, profiles);
			userRepository.create(user);
		};
		createMongoIndex();
		mqttClient.connect();
	}
	
	public void createMongoIndex() {
		MongoClient client = mongoConnection.getClient();
		MongoIterable<String> listCollectionNames = mongoConnection.connect().getDatabase().listCollectionNames();
		List<String> collections = new ArrayList<String>(
				Arrays.asList("discreteDevice", "measuringDevice", "analogOutputDevice"));
		listCollectionNames.forEach(collectionName -> collections.remove(collectionName.toString()));
		collections.forEach(collection -> {
			mongoConnection.getDatabase().createCollection(collection);
			mongoConnection.getDatabase().getCollection(collection).createIndex(Indexes.ascending("userId"));
		});
		client.close();
	}

}
