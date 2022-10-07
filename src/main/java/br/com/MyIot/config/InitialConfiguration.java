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
import br.com.MyIot.model.user.password.PasswordManager;
import br.com.MyIot.mqtt.MqttSystemClientSubscriber;
import br.com.MyIot.repository.config.MongoConnection;

/**
 * A classe <b>InitialConfiguration</b> que implementa a interface <b>ApplicationRunner</b> é responsável por fazer as configurações 
 * iniciais na aplicação no momento da inicialização
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
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
	
	@Autowired
	private PasswordManager passwordManager;
	
	/**
	 * Método implementado da interface <b>ApplicationRunner</b> que é executado no momento da inicialização da aplicação
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		createUserMaster();
		createMongoIndex();
		mqttSystemClientSubscribe();
		
	}
	
	/**
	 * Método responsável por criar o usuário master no momento da inicialização da aplicação, caso o mesmo ainda não tenha sido
	 * criado
	 */
	public void createUserMaster() {
		if(userRepository.findByEmail(new Email(userMasterEmail)).isEmpty()) {
			List<Profile> profiles = Arrays.asList(new Profile(ProfileType.ADMIN));
			String password = passwordManager.validateAndEncode(userMasterPassword);
			User user = new User(userMasterEmail, userMasterName, password, userMasterMqttPassword, profiles);
			userRepository.create(user);
		};
	}
	
	/**
	 * Método responsável por criar as coleções "discreteDevice", "measuringDevice", "analogOutputDevice" e gerar índices
	 * nas mesmas pelo parâmetro "userId", afim de melhorar o desempenho do banco de dados mongoDb no acesso a essas coleções
	 */
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
	
	/**
	 * Método responsável por fazer a aplicação se inscrever no tópico mqtt que receberá os dados a serem persistidos 
	 * no banco de dados
	 */
	public void mqttSystemClientSubscribe() {
		mqttClient.connect();
	}

}
