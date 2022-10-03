package br.com.MyIot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.MosquittoDynamicSecurity.dynsec.publisher.DynSecPublisher;
/**
 * A classe <b>BeanConfiguration</b> define os Beans utilzados na aplicação
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
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

	/**
	 * Método Bean para criar um PasswordEncoder
	 * @return renorna um encoder do tipo BcryptPasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * Método Bean para criar um DynSecPublisher, que é responsável por fazer o gerenciamento de clients e roles no Mosquitto
	 * @return Retorna um DynSecPublisher
	 */
	@Bean DynSecPublisher dynSecPublisher() {
		return new DynSecPublisher(uri, dynsecUsername, dynsecPassword, dynsecClientId);
	}

}
