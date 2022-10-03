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
	
	@PostMapping
	public ResponseEntity<String> create(@RequestBody DiscreteDeviceForm form) {
		String createdDeviceId = service.create(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id={id}").buildAndExpand(createdDeviceId)
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/id={id}")
	public ResponseEntity<String> deleteById(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.ok().body("Device with id " + id + " deleted!");
	}

	@DeleteMapping(value = "/all")
	public ResponseEntity<String> deleteAllByUser() {
		service.deleteAllByUser();
		return ResponseEntity.ok().body("Devices deleted!");
	}

	@PutMapping(value = "/id={id}")
	public ResponseEntity<DiscreteDeviceDto> updateById(@PathVariable String id,
			@RequestBody DiscreteDeviceForm form) {
		return ResponseEntity.ok().body(service.updateById(id, form));
	}
	
	@GetMapping(value = "/id={id}")
	public ResponseEntity<DiscreteDeviceDto> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findByIdDto(id));
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<DiscreteDeviceDto>> findAllByUser() {
		return ResponseEntity.ok().body(service.findAllByUser());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<DiscreteDeviceDto>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
}
