package br.com.MyIot.mqtt;

import org.springframework.stereotype.Service;

import br.com.MosquittoDynamicSecurity.dynsec.client.DynSecClient;
import br.com.MosquittoDynamicSecurity.dynsec.publisher.DynSecPublisher;
import br.com.MosquittoDynamicSecurity.dynsec.role.DynSecRole;

@Service
public class MqttStandardClientService {

	public void create(MqttStandardClient client) {
		MqttProperties mqttProperties = new MqttProperties();
		DynSecPublisher publisher = new DynSecPublisher(
				mqttProperties.getUri(), 
				mqttProperties.getAdminUsername(),
				mqttProperties.getAdminPassword(), 
				mqttProperties.getAdminClientId());
		DynSecClient dynSecClient = new DynSecClient(client.getUsername(), client.getPassword());
		DynSecRole role = new DynSecRole("role_" + client.getUsername());
		publisher.addCommand(dynSecClient.createCommand())
				 .addCommand(role.createCommand())
				 .addCommand(dynSecClient.addRoleCommand(role))
				 .addCommand(dynSecClient.disableCommand())
				 .publish();
	}

	public void delete(MqttStandardClient client) {
		MqttProperties mqttProperties = new MqttProperties();
		DynSecPublisher publisher = new DynSecPublisher(
				mqttProperties.getUri(), 
				mqttProperties.getAdminUsername(),
				mqttProperties.getAdminPassword(), 
				mqttProperties.getAdminClientId());
		DynSecClient dynSecClient = new DynSecClient(client.getUsername(), client.getPassword());
		DynSecRole role = new DynSecRole("role_" + client.getUsername());
		publisher.addCommand(role.deleteCommand())
				 .addCommand(dynSecClient.deleteCommand())
				 .publish();
	}

	public void enable(MqttStandardClient client) {
		MqttProperties mqttProperties = new MqttProperties();
		DynSecPublisher publisher = new DynSecPublisher(
				mqttProperties.getUri(),
				mqttProperties.getAdminUsername(),
				mqttProperties.getAdminPassword(), 
				mqttProperties.getAdminClientId());
		DynSecClient dynSecClient = new DynSecClient(client.getUsername(), client.getPassword());
		publisher.addCommand(dynSecClient.enableCommand()).publish();
	}

	public void disable(MqttStandardClient client) {
		MqttProperties mqttProperties = new MqttProperties();
		DynSecPublisher publisher = new DynSecPublisher(
				mqttProperties.getUri(), 
				mqttProperties.getAdminUsername(),
				mqttProperties.getAdminPassword(), 
				mqttProperties.getAdminClientId());
		DynSecClient dynSecClient = new DynSecClient(client.getUsername(), client.getPassword());
		publisher.addCommand(dynSecClient.disableCommand()).publish();
	}
}
