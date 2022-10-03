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

	public void create(String clientId, String username, String password) {
		DynSecClient dynSecClient = new DynSecClient(clientId, username, password);
		DynSecRole role = new DynSecRole("role_" + username);
		publisher.addCommand(dynSecClient.createWithIdCommand()).addCommand(role.createCommand())
				.addCommand(dynSecClient.addRoleCommand(role)).publish();
		List<ACLType> ACLs = Arrays.asList(ACLType.PUBLISH_CLIENT_RECEIVE, ACLType.PUBLISH_CLIENT_SEND,
				ACLType.SUBSCRIBE_LITERAL, ACLType.SUBSCRIBE_PATTERN, ACLType.UNSUBSCRIBE_LITERAL,
				ACLType.UNSUBSCRIBE_PATTERN);
		String topic = MqttTopic.getSystemTopic();
		ACLs.forEach(ACL -> {
			DynSecACL dynSecACL = new DynSecACL(ACL, topic, true);
			publisher.addCommand(role.addRoleACLCommand(dynSecACL));
		});
		publisher.publish();
	}

}
