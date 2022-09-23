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

import br.com.MyIot.repository.entity.MongoAnalogOutputDeviceEntity;

public class AnalogOutputDeviceCodec implements CollectibleCodec<MongoAnalogOutputDeviceEntity> {

	private Codec<Document> codec;

	public AnalogOutputDeviceCodec() {
		this.codec = new CodecProvider().getCodec();
	}

	@Override
	public void encode(BsonWriter writer, MongoAnalogOutputDeviceEntity entity, EncoderContext encoderContext) {
		Document document = new Document();
		document.append("_id", entity.getId()).append("userId", entity.getUserId())
				.append("location", entity.getLocation()).append("name", entity.getName())
				.append("output", entity.getOutput());

		codec.encode(writer, document, encoderContext);
	}

	@Override
	public Class<MongoAnalogOutputDeviceEntity> getEncoderClass() {
		return MongoAnalogOutputDeviceEntity.class;
	}

	@Override
	public MongoAnalogOutputDeviceEntity decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = codec.decode(reader, decoderContext);
		return new MongoAnalogOutputDeviceEntity(document.getObjectId("_id"), document.getObjectId("userId"),
				document.getString("location"), document.getString("name"), document.getInteger("output"));
	}

	@Override
	public MongoAnalogOutputDeviceEntity generateIdIfAbsentFromDocument(MongoAnalogOutputDeviceEntity entity) {
		return documentHasId(entity) ? entity.generateId() : entity;
	}

	@Override
	public boolean documentHasId(MongoAnalogOutputDeviceEntity entity) {
		return entity.getId() == null;
	}

	@Override
	public BsonValue getDocumentId(MongoAnalogOutputDeviceEntity entity) {
		if (documentHasId(entity)) {
			throw new IllegalStateException("This document has not id!");
		}
		return new BsonString(entity.getId().toHexString());
	}

}
