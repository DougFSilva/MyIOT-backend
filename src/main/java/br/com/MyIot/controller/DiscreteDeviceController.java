package br.com.MyIot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.MyIot.dto.device.DiscreteDeviceDto;
import br.com.MyIot.dto.device.DiscreteDeviceForm;
import br.com.MyIot.service.DiscreteDeviceService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * A classe <b>DiscreteDeviceController</b> define os endpoints nos quais os usuários poderão gerenciar os dispositivos do tipo
 * <b>DiscreteDevice</b>
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/discrete-device")
public class DiscreteDeviceController {

	@Autowired
	private DiscreteDeviceService service;
	
	@Operation(summary = "Criar dispositivo de sinal discreto", description = "Criar um dispositivo de sinal discreto no sistema")
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody DiscreteDeviceForm form) {
		String createdDeviceId = service.create(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id={id}").buildAndExpand(createdDeviceId)
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@Operation(summary = "Excluir dispositivo de sinal discreto", description = "Excluir um dispositivo de sinal discreto no sistema")
	@DeleteMapping(value = "/id={id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Excluir dispositivos de sinal discreto do usuário", description = "Excluir todos dispositivos de sinal discreto "
			+ "do usuário no sistema")
	@DeleteMapping(value = "/all")
	public ResponseEntity<Void> deleteAllByUser() {
		service.deleteAllByUser();
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Editar dispositivo de sinal discreto", description = "Editar um dispositivo de sinal discreto no sistema")
	@PutMapping(value = "/id={id}")
	public ResponseEntity<DiscreteDeviceDto> updateById(@PathVariable String id,
			@RequestBody DiscreteDeviceForm form) {
		return ResponseEntity.ok().body(service.updateById(id, form));
	}
	
	@Operation(summary = "Publicar status no Broker", description = "Publicar valor booleano de status no Broker Mosquitto")
	@PostMapping(value = "/id={id}/status={status}")
	public ResponseEntity<Void> publishStatusOnBrokerMqtt(@PathVariable String id, @PathVariable boolean status){
		service.publishOnBrokerMqtt(id, status);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Buscar dispositivo de sinal discreto", description = "Buscar um dispositivo de sinal discreto no sistema")
	@GetMapping(value = "/id={id}")
	public ResponseEntity<DiscreteDeviceDto> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findByIdDto(id));
	}

	@Operation(summary = "Buscar dispositivos de sinal discreto do usuário", description = "Buscar todos dispositivos de sinal discreto"
			+ "do usuário no sistema")
	@GetMapping(value = "/all")
	public ResponseEntity<List<DiscreteDeviceDto>> findAllByUser() {
		return ResponseEntity.ok().body(service.findAllByUser());
	}

	@Operation(summary = "Buscar dispositivos de sinal discreto", description = "Buscar todos dispositivos de sinal discreto no sistema. OBS.: "
			+ "Permitido somente para usuário ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<DiscreteDeviceDto>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
}
