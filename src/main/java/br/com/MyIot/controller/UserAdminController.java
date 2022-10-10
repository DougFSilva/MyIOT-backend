package br.com.MyIot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.MyIot.dto.user.UserAdminForm;
import br.com.MyIot.dto.user.UserDto;
import br.com.MyIot.service.UserAdminService;
import io.swagger.v3.oas.annotations.Operation;
/**
 * A classe <b>UserAdminController</b> define os endpoints que fazem o gerenciamento dos usuários e somente são acessíveis por
 * usuários de perfil "ADMIN"
 * do tipo <b>MeasuredValue</b>
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/admin")
@PreAuthorize("hasRole('ADMIN')")
public class UserAdminController {

	@Autowired
	private UserAdminService service;

	@Operation(summary = "Excluir usuário", description = "Excluir usuário no sistema")
	@DeleteMapping(value = "/id={id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.ok().body("User with id " + id + " deleted!");
	}

	@Operation(summary = "Atualizar usuário", description = "Atualizar usuário no sistema")
	@PutMapping(value = "/id={id}")
	public ResponseEntity<UserDto> updateById(@PathVariable String id, @RequestBody UserAdminForm form) {
		return ResponseEntity.ok().body(service.updateById(id, form));
	}

	@Operation(summary = "Buscar usuário por ID", description = "Buscar detalhes do usuário no sistema pelo ID")
	@GetMapping(value = "/id={id}")
	public ResponseEntity<UserDto> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findByIdDto(id));
	}

	@Operation(summary = "Buscar usuário por email", description = "Excluir usuário no sistema")
	@GetMapping(value = "/email={address}")
	public ResponseEntity<UserDto> findByEmail(@PathVariable String address) {
		return ResponseEntity.ok().body(service.findByEmailDto(address));
	}

	@Operation(summary = "Buscar usuários", description = "Buscar todos usuário no sistema")
	@GetMapping
	public ResponseEntity<List<UserDto>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
	
	@Operation(summary = "Buscar usuários a serem aprovados", description = "Buscar todos os usuários que não estão aprovados no sistema")
	@GetMapping(value = "/to-approve")
	public ResponseEntity<List<UserDto>> findUsersToApprove() {
		return ResponseEntity.ok().body(service.findUsersToApprove());
	}

	@Operation(summary = "Aprovar/desaporvar usuários", description = "Aprovar ou desaprovar usuários no sistema")
	@PutMapping(value = "/approve-registration/id={id}/{approved}")
	public ResponseEntity<UserDto> setApprovedRegistration(@PathVariable String id, @PathVariable boolean approved) {
		return ResponseEntity.ok().body(service.setApproveRegistration(id, approved));
	}

}
