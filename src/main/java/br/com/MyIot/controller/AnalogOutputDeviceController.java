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

import br.com.MyIot.dto.device.AnalogOutputDeviceDto;
import br.com.MyIot.dto.device.AnalogOutputDeviceForm;
import br.com.MyIot.service.AnalogOutputDeviceService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * A classe <b>AnalogOutputDeviceController</b> define os endpoints nos quais os
 * usuários poderão gerenciar os dispositivos do tipo <b>AnalogOutputDevice</b>
 * 
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/analog-output-device")
public class AnalogOutputDeviceController {

	@Autowired
	private AnalogOutputDeviceService service;

	@Operation(summary = "Criar dispositivo de controle analógico", description = "Criar um dispositivo de controle analógico no sistema")
	@PostMapping
	public ResponseEntity<String> create(@RequestBody AnalogOutputDeviceForm form) {
		String createdDeviceId = service.create(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id={id}").buildAndExpand(createdDeviceId)
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@Operation(summary = "Excluir dispositivo de controle analógico", description = "Excluir um dispositivo de controle analógico no sistema")
	@DeleteMapping(value = "/id={id}")
	public ResponseEntity<String> deleteById(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.ok().body("Device with id " + id + " deleted!");
	}

	@Operation(summary = "Excluir dispositivos do usuários", description = "Excluir todos os dispositivos do usuário no sistema")
	@DeleteMapping(value = "/all")
	public ResponseEntity<String> deleteAllByUser() {
		service.deleteAllByUser();
		return ResponseEntity.ok().body("Devices deleted!");
	}

	@Operation(summary = "Editar dispositivo de controle analógico", description = "Editar um dispositivo de controle analógico no sistema")
	@PutMapping(value = "/id={id}")
	public ResponseEntity<AnalogOutputDeviceDto> updateById(@PathVariable String id,
			@RequestBody AnalogOutputDeviceForm form) {
		return ResponseEntity.ok().body(service.updateById(id, form));
	}
	
	@Operation(summary = "Publicar output no Broker", description = "Publicar valor inteiro de output no Broker Mosquitto")
	@PostMapping(value = "/id={id}/output={output}")
	public ResponseEntity<Void> publishOutputOnBrokerMqtt(@PathVariable String id, @PathVariable Integer output){
		service.publishOnBrokerMqtt(id, output);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Buscar dispositivo de controle analógico", description = "Buscar um dispositivo de controle analógico no sistema")
	@GetMapping(value = "/id={id}")
	public ResponseEntity<AnalogOutputDeviceDto> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findByIdDto(id));
	}

	@Operation(summary = "Buscar dispositivos de controle analógico do usuário", description = "Buscar todos os dispositivos de controle "
			+ "analógico do usuário no sistema")
	@GetMapping(value = "/all")
	public ResponseEntity<List<AnalogOutputDeviceDto>> findAllByUser() {
		return ResponseEntity.ok().body(service.findAllByUser());
	}

	@Operation(summary = "Buscar dispositivos de controle analógico", description = "Buscar todos dispositivo de controle analógico no sistema. "
			+ "OBS.: Permitido somente usuário ADMIN")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<AnalogOutputDeviceDto>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
}
