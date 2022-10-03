package br.com.MyIot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.MosquittoDynamicSecurity.dynsec.publisher.DynSecPublisher;

@Configuration
public class BeanConfiguration {
	
	@Value("${mqtt.uri}")
	private String uri;

	@Value("${mqtt.dynsec.username}")
	private String dynsecUsername;

	@Value("${mqtt.dynsec.password}")
	private String dynsecPassword;

	@Value("${mqtt.dynsec.clientId}")
	private String dynsecClientId;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean DynSecPublisher dynSecPublisher() {
		return new DynSecPublisher(uri, dynsecUsername, dynsecPassword, dynsecClientId);
	}

}
