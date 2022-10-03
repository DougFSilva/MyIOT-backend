package br.com.MyIot.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.MosquittoDynamicSecurity.dynsec.client.DynSecClient;
import br.com.MosquittoDynamicSecurity.dynsec.publisher.DynSecPublisher;
import br.com.MosquittoDynamicSecurity.dynsec.role.DynSecRole;

/**
 * A classe <b>MqttStandardClient</b> é uma classe service responsável por fazer o gerenciamento de clients do tipo 
 * <b>MqttStandardClient</b>
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class MqttStandardClientService {
	
	@Autowired
	private DynSecPublisher publisher;

	public void create(MqttStandardClient client) {
		DynSecClient dynSecClient = new DynSecClient(client.getUsername(), client.getPassword());
		DynSecRole role = new DynSecRole("role_" + client.getUsername());
		publisher.addCommand(dynSecClient.createCommand())
				 .addCommand(role.createCommand())
				 .addCommand(dynSecClient.addRoleCommand(role))
				 .addCommand(dynSecClient.disableCommand())
				 .publish();
	}

	public void delete(MqttStandardClient client) {
		DynSecClient dynSecClient = new DynSecClient(client.getUsername(), client.getPassword());
		DynSecRole role = new DynSecRole("role_" + client.getUsername());
		publisher.addCommand(role.deleteCommand())
				 .addCommand(dynSecClient.deleteCommand())
				 .publish();
	}

	public void enable(MqttStandardClient client) {
		DynSecClient dynSecClient = new DynSecClient(client.getUsername(), client.getPassword());
		publisher.addCommand(dynSecClient.enableCommand()).publish();
	}

	public void disable(MqttStandardClient client) {
		DynSecClient dynSecClient = new DynSecClient(client.getUsername(), client.getPassword());
		publisher.addCommand(dynSecClient.disableCommand()).publish();
	}
}
