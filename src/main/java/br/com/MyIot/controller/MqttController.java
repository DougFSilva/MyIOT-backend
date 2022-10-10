package br.com.MyIot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.model.device.analogOutputDevice.AnalogOutputDevice;
import br.com.MyIot.model.device.discreteDevice.DiscreteDevice;
import br.com.MyIot.model.user.User;
import br.com.MyIot.mqtt.MqttDeviceRoleService;
import br.com.MyIot.mqtt.MqttStandardClient;
import br.com.MyIot.mqtt.MqttStandardClientService;
import br.com.MyIot.service.AnalogOutputDeviceService;
import br.com.MyIot.service.DiscreteDeviceService;
import br.com.MyIot.service.MeasuringDeviceService;
import br.com.MyIot.service.UserAdminService;
import io.swagger.v3.oas.annotations.Operation;

/**
 * A classe <b>MqttController</b> define os endpoints que fazem o gerenciamento de clients e roles mqtt e somente
 * são acessíveis por usuários de perfil "ADMIN"
 * do tipo <b>MeasuredValue</b>
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/mqtt")
@PreAuthorize("hasRole('ADMIN')")
public class MqttController {

	@Autowired
	private MqttStandardClientService clientService;

	@Autowired
	private MqttDeviceRoleService roleService;

	@Autowired
	private UserAdminService userService;

	@Autowired
	private AnalogOutputDeviceService analogOutputDeviceService;

	@Autowired
	private DiscreteDeviceService discreteDeviceService;

	@Autowired
	private MeasuringDeviceService measuringDeviceService;

	@Operation(summary = "Criar client Mosquitto", description = "Criar client Mosquitto no sistema. OBS.: Somente usuários ADMIN")
	@PostMapping(value = "/client/email={email}")
	public ResponseEntity<String> createStandardClient(@PathVariable String email) {
		User user = userService.findByEmail(email);
		MqttStandardClient client = new MqttStandardClient(user);
		clientService.create(client);
		return ResponseEntity.ok().body("Created client " + client.getUsername());
	}

	@Operation(summary = "Excluir client Mosquitto", description = "Excluir client Mosquitto no sistema. OBS.: Somente usuários ADMIN")
	@DeleteMapping(value = "/client/email={email}")
	public ResponseEntity<String> deleteStandardClient(@PathVariable String email) {
		User user = userService.findByEmail(email);
		MqttStandardClient client = new MqttStandardClient(user);
		clientService.delete(client);
		return ResponseEntity.ok().body("Deleted client " + client.getUsername());
	}

	@Operation(summary = "Habilitar client Mosquitto", description = "Habilitar client Mosquitto no sistema. OBS.: Somente usuários ADMIN")
	@PutMapping(value = "/client/enable/email={email}")
	public ResponseEntity<String> enableStandardClient(@PathVariable String email) {
		User user = userService.findByEmail(email);
		MqttStandardClient client = new MqttStandardClient(user);
		clientService.enable(client);
		return ResponseEntity.ok().body("Enabled client " + client.getUsername());
	}

	@Operation(summary = "Desabilitar client Mosquitto", description = "Desabilitar client Mosquitto no sistema. OBS.: Somente usuários ADMIN")
	@PutMapping(value = "/client/disable/email={email}")
	public ResponseEntity<String> disableStandardClient(@PathVariable String email) {
		User user = userService.findByEmail(email);
		MqttStandardClient client = new MqttStandardClient(user);
		clientService.disable(client);
		return ResponseEntity.ok().body("Disabled client " + client.getUsername());
	}

	@Operation(summary = "Criar role Mosquitto para dispositivo de comando analógico", description = "Criar role Mosquitto no sistema para "
			+ "dispositivo de comando analógico. OBS.: Somente usuários ADMIN")
	@PostMapping(value = "/role/analog-output-device/device-id={deviceId}")
	public ResponseEntity<String> createRoleByAnalogOutputDevice(@PathVariable String deviceId) {
		AnalogOutputDevice device = analogOutputDeviceService.findById(deviceId);
		roleService.create(device);
		return ResponseEntity.ok().body("Created role for device " + device.getName());
	}

	@Operation(summary = "Deletar role Mosquitto para dispositivo de comando analógico", description = "Deletar role Mosquitto no sistema para "
			+ "dispositivo de comando analógico. OBS.: Somente usuários ADMIN")
	@DeleteMapping(value = "/role/analog-output-device/device-id={deviceId}")
	public ResponseEntity<String> deleteRoleByAnalogOutputDevice(@PathVariable String deviceId) {
		AnalogOutputDevice device = analogOutputDeviceService.findById(deviceId);
		roleService.delete(device);
		return ResponseEntity.ok().body("Deleted role for device " + device.getName());
	}

	@Operation(summary = "Criar role Mosquitto para dispositivo de sinal discreto", description = "Criar role Mosquitto no sistema para "
			+ "dispositivo de sinal discreto. OBS.: Somente usuários ADMIN")
	@PostMapping(value = "/role/discrete-device/device-id={deviceId}")
	public ResponseEntity<String> createRoleByDiscreteDevice(@PathVariable String deviceId) {
		DiscreteDevice device = discreteDeviceService.findById(deviceId);
		roleService.create(device);
		return ResponseEntity.ok().body("Created role for device " + device.getName());
	}

	@Operation(summary = "Deletar role Mosquitto para dispositivo de sinal discreto", description = "Deletar role Mosquitto no sistema para "
			+ "dispositivo de sinal discreto. OBS.: Somente usuários ADMIN")
	@DeleteMapping(value = "/role/discrete-device/device-id={deviceId}")
	public ResponseEntity<String> deleteRoleByDiscreteDevice(@PathVariable String deviceId) {
		DiscreteDevice device = discreteDeviceService.findById(deviceId);
		roleService.delete(device);
		return ResponseEntity.ok().body("Deleted role for device " + device.getName());
	}

	@Operation(summary = "Criar role Mosquitto para dispositivo de medição", description = "Criar role Mosquitto no sistema para "
			+ "dispositivo de medição. OBS.: Somente usuários ADMIN")
	@PostMapping(value = "/role/measuring-device/device-id={deviceId}")
	public ResponseEntity<String> createRoleBymeasuringDevice(@PathVariable String deviceId) {
		MeasuringDevice device = measuringDeviceService.findById(deviceId);
		roleService.create(device);
		return ResponseEntity.ok().body("Created role for device " + device.getName());
	}

	@Operation(summary = "deletar role Mosquitto para dispositivo de medição", description = "Deletar role Mosquitto no sistema para "
			+ "dispositivo de medição. OBS.: Somente usuários ADMIN")
	@DeleteMapping(value = "/role/measuring-device/device-id={deviceId}")
	public ResponseEntity<String> deleteRoleByMeasuringDevice(@PathVariable String deviceId) {
		MeasuringDevice device = measuringDeviceService.findById(deviceId);
		roleService.delete(device);
		return ResponseEntity.ok().body("Deleted role for device " + device.getName());
	}

}
