package br.com.MyIot.repository.config;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Service
public class MongoConnection {

	@Value("${mongodb.uri}")
	private String uri;

	private MongoClient client;

	public MongoConnection connect(Codec<?> codec) {
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(codec));
		MongoClientSettings clientSettings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(uri))
				.codecRegistry(codecRegistry)
				.build();
		client = MongoClients.create(clientSettings);
		return this;
	}

	public MongoConnection connect() {
		client = MongoClients.create(new ConnectionString(uri));
		return this;
	}

	public void close() {
		client.close();
	}

	public MongoClient getClient() {
		return client;
	}

	public String getUri() {
		return uri;
	}

	public MongoDatabase getDatabase() {
		return client.getDatabase("iotProject");
	}

}
