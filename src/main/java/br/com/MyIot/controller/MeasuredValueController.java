package br.com.MyIot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.MyIot.dto.device.MeasuredValueDto;
import br.com.MyIot.dto.device.MeasuredValueForm;
import br.com.MyIot.model.device.MeasuringDevice.DateFilter;
import br.com.MyIot.service.MeasuredValueService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * A classe <b>MeasuredValueController</b> define os endpoints nos quais os
 * usuários poderão gerenciar os valores de medição do tipo <b>MeasuredValue</b>
 * 
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/measured-value")
public class MeasuredValueController {

	@Autowired
	private MeasuredValueService service;

	@Operation(summary = "Criar valor de medição", description = "Criar valor de medição para um dispositivo de medição no sistema")
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody MeasuredValueForm form) {
		String createValueId = service.create(form);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id={id}").buildAndExpand(createValueId)
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@Operation(summary = "Excluir valor de medição", description = "Excluir valor de medição para um dispositivo de medição no sistema")
	@DeleteMapping(value = "/device-id={deviceId}/id={id}")
	public ResponseEntity<Void> deleteById(@PathVariable String deviceId, @PathVariable String id) {
		service.deleteById(deviceId, id);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Excluir valores de medição", description = "Excluir todos valores de medição de um dispositivo de medição no sistema")
	@DeleteMapping(value = "/device-id={deviceId}")
	public ResponseEntity<Void> deleteAllByDeviceId(@PathVariable String deviceId) {
		service.deleteAllByDevice(deviceId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Excluir valores de medição por período", description = "Excluir valores de medição de um dispositivo de medição "
			+ "por um período de tempo especificado")
	@DeleteMapping(value = "/device-id={deviceId}/date={initialDate}to{finalDate}/time={initialTime}to{finalTime}")
	public ResponseEntity<Void> deleteByTimeRange(
			@PathVariable String deviceId,
			@PathVariable String initialDate,
			@PathVariable String finalDate, 
			@PathVariable String initialTime,
			@PathVariable String finalTime) {
		DateFilter filter = new DateFilter(initialDate, finalDate, initialTime, finalTime);
		service.deleteByTimeRange(deviceId, filter);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Buscar valores de medição", description = "Buscar valores de medição de um dispositivo de medição no sistema")
	@GetMapping(value = "/device-id={deviceId}/limit={limit}")
	public ResponseEntity<List<MeasuredValueDto>> findAllByDevice(@PathVariable String deviceId, @PathVariable Integer limit) {
		return ResponseEntity.ok().body(service.findAllByDevice(deviceId, limit));
	}

	@Operation(summary = "buscar valores de medição por período", description = "Buscar valores de medição de um dispositivo de medição"
			+ "por um período de tempo especificado")
	@GetMapping(value = "/device-id={deviceId}/date={initialDate}to{finalDate}/time={initialTime}to{finalTime}/limit={limit}")
	public ResponseEntity<List<MeasuredValueDto>> findAllByDeviceAndTimeRange(
			@PathVariable String deviceId,
			@PathVariable String initialDate,
			@PathVariable String finalDate, 
			@PathVariable String initialTime,
			@PathVariable String finalTime,
			@PathVariable Integer limit) {
		DateFilter filter = new DateFilter(initialDate, finalDate, initialTime, finalTime);
		return ResponseEntity.ok()
				.body(service.findAllByDeviceAndTimeRange(deviceId, filter, limit));
	}
}
