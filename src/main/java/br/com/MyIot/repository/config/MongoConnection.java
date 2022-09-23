package br.com.MyIot.repository.config;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoConnection {

	@Value("${mongodb.uri}")
	private String uri;

	@Value("${mongodb.database}")
	private String database;
	
	public MongoClient getClient(Codec<?> codec) {
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(codec));
		MongoClientSettings clientSettings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(uri)).codecRegistry(codecRegistry).build();
		MongoClient client = MongoClients.create(clientSettings);
		return client;

	}

	public MongoClient getSimpleClient() {
		return MongoClients.create(uri);
	}

	public String getUri() {
		return uri;
	}

	public MongoDatabase getDatabase(MongoClient client) {
		return client.getDatabase(database);
	}

}
