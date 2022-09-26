package br.com.MyIot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody UserForm form) {
		String userCreatedId = service.create(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id={id}").buildAndExpand(userCreatedId)
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/id={id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.ok().body("User with id " + id + " deleted!");
	}

	@PutMapping(value = "/id={id}")
	public ResponseEntity<UserDto> updateById(@PathVariable String id, @RequestBody UserForm form) {
		return ResponseEntity.ok().body(service.updateById(id, form));
	}

	@PutMapping(value = "/update-password")
	public ResponseEntity<String> updatedPasswordById(@RequestBody UserUpdatePasswordForm form) {
		service.updatePasswordById(form);
		return ResponseEntity.ok().body("Password updated successfully!");
	}

	@GetMapping(value = "/id={id}")
	public ResponseEntity<UserDto> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findByIdDto(id));
	}

	@GetMapping(value = "/email={address}")
	public ResponseEntity<UserDto> findByEmail(@PathVariable String address) {
		return ResponseEntity.ok().body(service.findByEmail(address));
	}

	@GetMapping
	public ResponseEntity<List<UserDto>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@PutMapping(value = "/approve-registration/id={id}/{approved}")
	public ResponseEntity<UserDto> setApprovedRegistration(@PathVariable String id, @PathVariable boolean approved) {
		return ResponseEntity.ok().body(service.setApproveRegistration(id, approved));
	}

}
