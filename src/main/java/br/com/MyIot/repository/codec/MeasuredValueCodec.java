package br.com.MyIot.repository.codec;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import br.com.MyIot.repository.entity.MongoMeasuredValueEntity;

/**
 * A classe <b>MeasuredValueCodec</b> define um codec para um objeto do tipo
 * <b>MeasuredValue</b> ser codificado e decodificado quando se relaciona com o
 * banco de dados MongoDb
 * 
 * @since Out 2022
 * @version 1.0
 */
public class MeasuredValueCodec implements CollectibleCodec<MongoMeasuredValueEntity> {

	private Codec<Document> codec;

	public MeasuredValueCodec() {
		this.codec = new CodecProvider().getCodec();
	}

	@Override
	public void encode(BsonWriter writer, MongoMeasuredValueEntity entity, EncoderContext encoderContext) {
		Document document = new Document();
		document.append("deviceId", entity.getDeviceId())
				.append("timestamp", entity.getTimeStamp())
				.append("values",
				entity.getValues());
		codec.encode(writer, document, encoderContext);
	}

	@Override
	public Class<MongoMeasuredValueEntity> getEncoderClass() {
		return MongoMeasuredValueEntity.class;
	}

	@Override
	public MongoMeasuredValueEntity decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = codec.decode(reader, decoderContext);
		Instant instant = document.getDate("timestamp").toInstant();
		LocalDateTime timestamp = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
		MongoMeasuredValueEntity entity = new MongoMeasuredValueEntity(document.getObjectId("_id"),
				document.getObjectId("deviceId"), document.getList("values", Double.class), timestamp);
		return entity;
	}

	@Override
	public MongoMeasuredValueEntity generateIdIfAbsentFromDocument(MongoMeasuredValueEntity entity) {
		return documentHasId(entity) ? entity.generateId() : entity;
	}

	@Override
	public boolean documentHasId(MongoMeasuredValueEntity entity) {
		return entity.getId() == null;
	}

	@Override
	public BsonValue getDocumentId(MongoMeasuredValueEntity entity) {
		if (documentHasId(entity)) {
			throw new IllegalStateException("This document has not id!");
		}
		return new BsonString(entity.getId().toHexString());
	}

}