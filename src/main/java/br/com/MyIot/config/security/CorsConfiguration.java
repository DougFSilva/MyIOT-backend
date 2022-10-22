package br.com.MyIot.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * A classe <b>CorsConfiguration</b> que implementa a interface
 * <b>WebMvcConfigurer</b> mapeia as origens permitidas para acessar os
 * endpoints da aplicação
 * 
 * @since Out 2022
 * @version 1.0
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
	
	@Value("${frontend.origin}")
	private String origin;

	/**
	 * Método implementado da interface <b>WebMvcConfigurer</b> que mapeia as
	 * origens permitidas para acessar os endpoints da aplicação
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(origin).allowedMethods("*");
	}
}