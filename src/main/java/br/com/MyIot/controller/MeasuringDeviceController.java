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

import br.com.MyIot.dto.device.MeasuringDeviceDto;
import br.com.MyIot.dto.device.MeasuringDeviceForm;
import br.com.MyIot.service.MeasuringDeviceService;

@RestController
@RequestMapping(value = "/measuring-device")
public class MeasuringDeviceController {

	@Autowired
	private MeasuringDeviceService service;
	
	@PostMapping
	public ResponseEntity<String> create(@RequestBody MeasuringDeviceForm form) {
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
	public ResponseEntity<String> deleteAllByUserId() {
		service.deleteAllByUser();
		return ResponseEntity.ok().body("Devices Deleted!");
	}

	@PutMapping(value = "/id={id}")
	public ResponseEntity<MeasuringDeviceDto> updateById(@PathVariable String id,
			@RequestBody MeasuringDeviceForm form) {
		return ResponseEntity.ok().body(service.updateById(id, form));
	}
	
	@GetMapping(value = "/id={id}")
	public ResponseEntity<MeasuringDeviceDto> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findByIdDto(id));
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<MeasuringDeviceDto>> findAllByUserId() {
		return ResponseEntity.ok().body(service.findAllByUser());
	}

	@GetMapping
	public ResponseEntity<List<MeasuringDeviceDto>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}
}
