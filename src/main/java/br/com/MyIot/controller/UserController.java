package br.com.MyIot.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.MyIot.dto.user.UserDto;
import br.com.MyIot.dto.user.UserForm;
import br.com.MyIot.dto.user.UserUpdatePasswordForm;
import br.com.MyIot.service.UserService;

/**
 * A classe <b>UserController</b> define os endpoints nos quais os usuários poderão gerenciar a própria conta
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService service;

	@PostMapping
	ResponseEntity<String> create(@RequestBody UserForm form){
		String createdUserId = service.create(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id={id}").buildAndExpand(createdUserId).toUri();
		return ResponseEntity.created(uri).body("thanks for using our system!");
	}
	
	@DeleteMapping
	ResponseEntity<String> delete(){
		service.delete();
		return ResponseEntity.ok().body("User deleted, goodbye!");
	}
	
	@PutMapping
	ResponseEntity<UserDto> update(@RequestBody UserForm form){
		return ResponseEntity.ok().body(service.update(form));
	}
	
	@PutMapping(value = "/password")
	ResponseEntity<UserDto> updatePasword(@RequestBody UserUpdatePasswordForm form){
		return ResponseEntity.ok().body(service.updatePasswordById(form));
	}
	
	@GetMapping
	ResponseEntity<UserDto> findUser(){
		return ResponseEntity.ok().body(service.findByIdDto());
	}
}
