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

import br.com.MyIot.dto.AnalogOutputDeviceDto;
import br.com.MyIot.dto.AnalogOutputDeviceForm;
import br.com.MyIot.service.AnalogOutputDeviceService;

@RestController
@RequestMapping(value = "/analog-output-device")
public class AnalogOutputDeviceController {

	@Autowired
	private AnalogOutputDeviceService service;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody AnalogOutputDeviceForm form) {
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

	@DeleteMapping(value = "/user-id={userId}")
	public ResponseEntity<String> deleteAllByUserId(@PathVariable String userId) {
		service.deleteAllByUser(userId);
		return ResponseEntity.ok().body("Delete Devices of user with id " + userId);
	}

	@PutMapping(value = "/id={id}")
	public ResponseEntity<AnalogOutputDeviceDto> updateById(@PathVariable String id,
			@RequestBody AnalogOutputDeviceForm form) {
		return ResponseEntity.ok().body(service.updateById(id, form));
	}

	@GetMapping(value = "/id={id}")
	public ResponseEntity<AnalogOutputDeviceDto> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findByIdDto(id));
	}

	@GetMapping(value = "/user-id/{userId}")
	public ResponseEntity<List<AnalogOutputDeviceDto>> findAllByUserId(@PathVariable String userId) {
		return ResponseEntity.ok().body(service.findAllByUser(userId));
	}

	@GetMapping
	public ResponseEntity<List<AnalogOutputDeviceDto>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
}