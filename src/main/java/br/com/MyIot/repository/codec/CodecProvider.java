package br.com.MyIot.repository.codec;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientSettings;

/**
 * A classe <b>CodecProvider</b> define um tipo de codec default para geração dos codecs
 * @since Out 2022
 * @version 1.0
 */
@Service
public class CodecProvider {

	private Codec<Document> codec;

	public CodecProvider() {
		Codec<Document> codec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
		this.codec = codec;
	}

	public Codec<Document> getCodec() {
		return this.codec;
	}
}
