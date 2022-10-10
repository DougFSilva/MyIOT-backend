package br.com.MyIot.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.MyIot.model.user.UserRepository;
/**
 * A classe <b>SecurityConfiguration</b> que extende a classe <b>WebSecurityConfigurerAdapter</b> define as configurações de 
 * segurança do sistema
 * @since Out 2022
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	/**
	 * Método sobrescrito da classe WebSecurityConfigurerAdapter que configura a classe responsável por buscar o usuário
	 * no banco de dados e o tipo de encoder de password
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	 * Método sobrescrito da classe WebSecurityConfigurerAdapter que configura cors, desabilita o csrf, define quais endpoints
	 * permitidos e quais precisam de autenticação, configura o sistema como stateless e o filtro de autenticação.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.antMatchers(HttpMethod.POST, "/auth").permitAll()
				.antMatchers(HttpMethod.POST, "/user").permitAll()
				.antMatchers(HttpMethod.GET, "/v3/**").permitAll()
				.antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
				.anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
				.and().addFilterBefore(new AuthenticationFilter(tokenService, userRepository),
						UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * Método sobrescrito da classe WebSecurityConfigurerAdapter que configura os endpoints a serem ignorados pelo SpringSecurity
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

}
