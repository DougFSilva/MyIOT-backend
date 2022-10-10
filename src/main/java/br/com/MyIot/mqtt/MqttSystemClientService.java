package br.com.MyIot.mqtt;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.MosquittoDynamicSecurity.dynsec.ACL.ACLType;
import br.com.MosquittoDynamicSecurity.dynsec.ACL.DynSecACL;
import br.com.MosquittoDynamicSecurity.dynsec.client.DynSecClient;
import br.com.MosquittoDynamicSecurity.dynsec.publisher.DynSecPublisher;
import br.com.MosquittoDynamicSecurity.dynsec.role.DynSecRole;

/**
 * A classe <b>MqttSystemClientService</b> é uma classe service responsável por fazer o gerenciamento do client mqtt da acpliacação
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class MqttSystemClientService {

	@Autowired
	private DynSecPublisher publisher;

	public void create(String username, String password) {
		DynSecClient dynSecClient = new DynSecClient(username, password);
		DynSecRole role = new DynSecRole("role_" + username);
		publisher.addCommand(dynSecClient.createCommand())
				 .addCommand(role.createCommand())
				 .addCommand(dynSecClient.addRoleCommand(role))
				 .publish();
		List<ACLType> ACLsToSubscribe = Arrays.asList(
				ACLType.SUBSCRIBE_LITERAL, 
				ACLType.SUBSCRIBE_PATTERN, 
				ACLType.UNSUBSCRIBE_LITERAL,
				ACLType.UNSUBSCRIBE_PATTERN);
		List<ACLType> ACLsToPublish = Arrays.asList(ACLType.PUBLISH_CLIENT_RECEIVE, ACLType.PUBLISH_CLIENT_SEND);
		ACLsToSubscribe.forEach(ACL -> {
			DynSecACL dynSecACL = new DynSecACL(ACL, MqttTopic.getSystemTopicToSubscribe(), true);
			publisher.addCommand(role.addRoleACLCommand(dynSecACL));
		});
		ACLsToPublish.forEach(ACL -> {
			DynSecACL dynSecACL = new DynSecACL(ACL, MqttTopic.getSystemTopicToPublish(), true);
			publisher.addCommand(role.addRoleACLCommand(dynSecACL));
		});
		publisher.publish();
	}

}
