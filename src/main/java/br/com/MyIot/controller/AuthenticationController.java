package br.com.MyIot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.MyIot.config.security.TokenService;
import br.com.MyIot.dto.TokenDto;
import br.com.MyIot.dto.user.LoginForm;
import br.com.MyIot.model.user.User;
import io.swagger.v3.oas.annotations.Operation;

/**
 * A classe <b>AuthenticationController</b> define os endpoints para fazer login na aplicação
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@Operation(summary = "Autenticação", description = "Autenticação por usuário e senha")
	@PostMapping
	public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken login = form.convert();
		try {
			Authentication authentication = authenticationManager.authenticate(login);
			User user = (User) authentication.getPrincipal();
			String token = tokenService.generate(authentication);
			TokenDto tokenDTO = new TokenDto(token, "Bearer ", user.getName(), user.getProfiles());
			return ResponseEntity.ok(tokenDTO);

		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(e);
		}
	}

}
