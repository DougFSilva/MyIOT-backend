package br.com.MyIot.repository.codec;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import br.com.MyIot.repository.entity.MongoDiscreteDeviceEntity;

public class DiscreteDeviceCodec implements CollectibleCodec<MongoDiscreteDeviceEntity> {

	private Codec<Document> codec;

	public DiscreteDeviceCodec() {
		this.codec = new CodecProvider().getCodec();
	}

	@Override
	public void encode(BsonWriter writer, MongoDiscreteDeviceEntity entity,
			EncoderContext encoderContext) {
		Document document = new Document();
		document.append("_id", entity.getId())
				.append("userId", entity.getUserId())
				.append("location", entity.getLocation())
				.append("name", entity.getName())
				.append("status", entity.isStatus());
		codec.encode(writer, document, encoderContext);
	}

	@Override
	public Class<MongoDiscreteDeviceEntity> getEncoderClass() {
		return MongoDiscreteDeviceEntity.class;
	}

	@Override
	public MongoDiscreteDeviceEntity decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = codec.decode(reader, decoderContext);
		return new MongoDiscreteDeviceEntity(document.getObjectId("_id"), document.getObjectId("userId"),
				document.getString("location"), document.getString("name"), document.getBoolean("status"));
	}

	@Override
	public MongoDiscreteDeviceEntity generateIdIfAbsentFromDocument(MongoDiscreteDeviceEntity entity) {
		return documentHasId(entity) ? entity.generateId() : entity;
	}

	@Override
	public boolean documentHasId(MongoDiscreteDeviceEntity entity) {
		return entity.getId() == null;
	}

	@Override
	public BsonValue getDocumentId(MongoDiscreteDeviceEntity entity) {
		if (documentHasId(entity)) {
			throw new IllegalStateException("This document has not id!");
		}
		return new BsonString(entity.getId().toHexString());
	}

}
