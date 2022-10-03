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

import br.com.MyIot.repository.entity.MongoMeasuringDeviceEntity;

/**
 * A classe <b>MeasuringDeviceCodec</b> define um codec para um objeto do tipo <b>MeasuringDevice</b> ser codificado e 
 * decodificado quando se relaciona com o banco de dados MongoDb
 * @since Out 2022
 * @version 1.0
 */
public class MeasuringDeviceCodec implements CollectibleCodec<MongoMeasuringDeviceEntity> {

	private Codec<Document> codec;

	public MeasuringDeviceCodec() {
		this.codec = new CodecProvider().getCodec();
	}

	@Override
	public void encode(BsonWriter writer, MongoMeasuringDeviceEntity entity,
			EncoderContext encoderContext) {
		Document document = new Document();
		document.append("_id", entity.getId())
				.append("userId", entity.getUserId())
				.append("location", entity.getLocation())
				.append("name", entity.getName())
				.append("keyNames", entity.getKeyNames());
		this.codec.encode(writer, document, encoderContext);
	}

	@Override
	public Class<MongoMeasuringDeviceEntity> getEncoderClass() {
		return MongoMeasuringDeviceEntity.class;
	}

	@Override
	public MongoMeasuringDeviceEntity decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = codec.decode(reader, decoderContext);
		return new MongoMeasuringDeviceEntity(document.getObjectId("_id"), document.getObjectId("userId"),
				document.getString("location"), document.getString("name"), document.getList("keyNames", String.class));
	}

	@Override
	public MongoMeasuringDeviceEntity generateIdIfAbsentFromDocument(
			MongoMeasuringDeviceEntity entity) {
		return documentHasId(entity) ? entity.generateId()
				: entity;
	}

	@Override
	public boolean documentHasId(MongoMeasuringDeviceEntity entity) {
		return entity.getId() == null;
	}

	@Override
	public BsonValue getDocumentId(MongoMeasuringDeviceEntity entity) {
		if (documentHasId(entity)) {
			throw new IllegalStateException("This document has not id!");
		}
		return new BsonString(entity.getId().toHexString());

	}

}
