package br.com.MyIot.repository.codec;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientSettings;

@Component
public class CodecProvider {

	private Codec<Document> codec;

	public CodecProvider() {
		Codec<Document> codec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
		this.codec = codec;
	}

	@Bean
	public Codec<Document> getCodec() {
		return this.codec;
	}
}
