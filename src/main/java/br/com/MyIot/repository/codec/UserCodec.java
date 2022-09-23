package br.com.MyIot.repository.codec;

import java.util.List;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.repository.MongoProfile;
import br.com.MyIot.repository.entity.MongoUserEntity;

public class UserCodec implements CollectibleCodec<MongoUserEntity> {

	private Codec<Document> codec;

	public UserCodec() {
		this.codec = new CodecProvider().getCodec();
	}

	@Override
	public void encode(BsonWriter writer, MongoUserEntity entity, EncoderContext encoderContext) {
		List<Document> profilesDocuments = entity.getProfiles()
				.stream()
				.map(profile -> new Document("type", profile.getType().getDescription()).append("authority",
						profile.getAuthority()))
				.toList();
		Document document = new Document();
		document.append("_id", entity.getId())
				.append("email", entity.getEmail())
				.append("name", entity.getName())
				.append("password", entity.getPassword())
				.append("clientMqttPassword", entity.getClientMqttPassword())
				.append("approvedRegistration", entity.isApporvedRegistration())
 				.append("profiles", profilesDocuments);
		codec.encode(writer, document, encoderContext);

	}

	@Override
	public Class<MongoUserEntity> getEncoderClass() {
		return MongoUserEntity.class;
	}

	@Override
	public MongoUserEntity decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = codec.decode(reader, decoderContext);
		List<Document> mongoProfileDocuments = document.getList("profiles", Document.class);
		List<MongoProfile> mongoProfiles = mongoProfileDocuments
				.stream()
				.map(profileDocument -> new MongoProfile(ProfileType.toEnum(profileDocument.getString("type"))))
				.toList();
		return new MongoUserEntity(document.getObjectId("_id"), 
								   document.getString("email"), 
								   document.getString("name"),
								   document.getString("password"), 
								   document.getString("clientMqttPassword"), 
								   document.getBoolean("approvedRegistration"), 
								   mongoProfiles);
	}

	@Override
	public MongoUserEntity generateIdIfAbsentFromDocument(MongoUserEntity entity) {
		return documentHasId(entity) ? entity.generateId() : entity;
	}

	@Override
	public boolean documentHasId(MongoUserEntity entity) {
		return entity.getId() == null;
	}

	@Override
	public BsonValue getDocumentId(MongoUserEntity entity) {
		if (!documentHasId(entity)) {
			throw new IllegalStateException("This document has not id!");
		}
		return new BsonString(entity.getId().toHexString());
	}

}
