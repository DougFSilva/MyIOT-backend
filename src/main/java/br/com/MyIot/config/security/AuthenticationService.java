package br.com.MyIot.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.MyIot.model.user.Email;
import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
/**
 * A classe <b>AuthenticationService</b> que implementa a interface <b>UserDetailsService</b> é uma classe service utilizada para 
 * buscar o usuário no banco de dados através do username
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	/**
	 * Método implementado da interface <b>UserDetailsService</b> que faz a busca do usuário através do username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByEmail(new Email(username));
		return user.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found in database!"));
	}

}
