package br.com.MyIot.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.MyIot.model.user.User;
import br.com.MyIot.model.user.UserRepository;
/**
 * A classe <b>AuthenticationFilter</b> que extende a classe <b>OncePerRequestFilter</b> define um filtro de autenticação
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
public class AuthenticationFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	private UserRepository userRepository;

	public AuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	/**
	 * Método sobrescrito da classe <b>OncePerRequestFilter</b>
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = tokenRecover(request);
		boolean valid = tokenService.valid(token);
		if (valid) {
			authentication(token);
		}

		filterChain.doFilter(request, response);
	}

	/**Método que recebe um token em forma de String e faz a autenticação no sistema
	 * @param token
	 */
	private void authentication(String token) {
		String userId = tokenService.getUserId(token);
		Optional<User> user = userRepository.findById(userId);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get(), null,
				user.get().getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * Método que recebe uma requisição http e recupera o token existente no parâmetro "Authorization" do cabeçalho ou então 
	 * no parâmetro existente na URI da solicitação (Utilizado nesse caso para receber o token pela conexão websocket)
	 * @param request
	 * @return Retorna o token em forma de String
	 */
	private String tokenRecover(HttpServletRequest request) {
		String headerToken = request.getHeader("Authorization");
		if (headerToken == null || headerToken.isEmpty() || !headerToken.startsWith("Bearer ")) {
			String requestParameter = request.getParameter("token");
			if(requestParameter != null && !requestParameter.isEmpty()) {
				return requestParameter;
			}
			return null;
		}
		return headerToken.substring(7, headerToken.length());
	}

}
