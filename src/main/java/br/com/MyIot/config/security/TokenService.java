package br.com.MyIot.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.MyIot.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * A classe <b>TokenService</b> é uma classe service utilizada gerar e validar o token JWT 
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private long expiration;

	@Value("${jwt.secret}")
	private String secret;

	/**
	 * Método que gera um token JWT, setando o emissor, o subject, a data da geração, a data da expiração do token e a String 
	 * secret
	 * @param authentication
	 * @return Retorna um token do tipo String
	 */
	public String generate(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		return Jwts.builder().setIssuer("API MyIot")
				.setSubject(user.getId()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + (expiration * 1000 * 60)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	/**
	 * Método que recebe um token JWT e válida
	 * @param token
	 * @return Retorna true se for válido e false se inválido
	 */
	public boolean valid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Método que recebe um token JWT e busca o subject setado nele
	 * @param token
	 * @return Retorna um String com o valor do subject
	 */
	public String getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
}
