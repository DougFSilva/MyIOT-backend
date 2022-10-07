package br.com.MyIot.repository.config;

import java.util.Arrays;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * A classe <b>MongoConnection</b> define uma conexão a um banco de dados MongoDb
 * @since Out 2022
 * @version 1.0
 */
@Service
public class MongoConnection {

	@Value("${mongodb.host}")
	private String host;
	
	@Value("${mongodb.port}")
	private Integer port;
	
	@Value("${mongodb.username}")
	private String username;
	
	@Value("${mongodb.password}")
	private String password;
	
	@Value("${mongodb.database}")
	private String database;

	private MongoClient client;

	/**
	 * Recebe um codec e faz a conexão com o banco de dados
	 * @param codec
	 * @return Retorna a mesma classe para permitir encadeamento de métodos
	 */
	public MongoConnection connect(Codec<?> codec) {
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(codec));
		MongoClientSettings clientSettings = MongoClientSettings.builder()
				.applyToClusterSettings(builder ->  builder.hosts(Arrays.asList(new ServerAddress(host, port))))
				.credential(MongoCredential.createCredential(username,"admin", password.toCharArray()))
				.codecRegistry(codecRegistry)
				.build();
		client = MongoClients.create(clientSettings);
		return this;
	}

	/**
	 * Faz uma conexão simples com o banco de dados MongoDb sem codec
	 * @return Retorna a mesma classe para permitir encadeamento de métodos
	 */
	public MongoConnection connect() {
		MongoClientSettings clientSettings = MongoClientSettings.builder()
				.applyToClusterSettings(builder ->  builder.hosts(Arrays.asList(new ServerAddress(host, port))))
				.credential(MongoCredential.createCredential(username,"admin", password.toCharArray()))
				.build();
		client = MongoClients.create(clientSettings);
		return this;
	}

	public void close() {
		client.close();
	}

	public MongoClient getClient() {
		return client;
	}

	public String getHost() {
		return host;
	}
	
	public Integer getPort() {
		return port;
	}

	public MongoDatabase getDatabase() {
		return client.getDatabase(database);
	}

}
