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

public class AuthenticationFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	private UserRepository userRepository;

	public AuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

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

	private void authentication(String token) {
		String userId = tokenService.getUserId(token);
		Optional<User> user = userRepository.findById(userId);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get(), null,
				user.get().getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String tokenRecover(HttpServletRequest request) {
		String headerToken = request.getHeader("Authorization");
		String requestParameter = request.getParameter("token");
		if(headerToken == null && requestParameter != null) {
			return requestParameter;
		}
		if (headerToken == null || headerToken.isEmpty() || !headerToken.startsWith("Bearer ")) {
			if(request.getParameter("token") != null)
			return null;
		}
		return headerToken.substring(7, headerToken.length());
	}

}
