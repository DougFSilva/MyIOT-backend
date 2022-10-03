package br.com.MyIot.mqtt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.MosquittoDynamicSecurity.dynsec.ACL.ACLType;
import br.com.MosquittoDynamicSecurity.dynsec.ACL.DynSecACL;
import br.com.MosquittoDynamicSecurity.dynsec.publisher.DynSecPublisher;
import br.com.MosquittoDynamicSecurity.dynsec.role.DynSecRole;
import br.com.MyIot.model.device.Device;

@Service
public class MqttDeviceRoleService {
	
	@Autowired
	private DynSecPublisher publisher;

	public void create(Device device) {
		DynSecRole role = new DynSecRole("role_" + device.getUser().getEmail().getAddress());
		List<DynSecACL> ACLs = new ArrayList<>();
		ACLs.add(new DynSecACL(ACLType.PUBLISH_CLIENT_SEND, MqttTopic.getDeviceTopicToPersit(device), true));
		ACLs.add(new DynSecACL(ACLType.PUBLISH_CLIENT_SEND, MqttTopic.getDeviceTopic(device), true));
		ACLs.add(new DynSecACL(ACLType.SUBSCRIBE_LITERAL, MqttTopic.getDeviceTopic(device), true));
		ACLs.add(new DynSecACL(ACLType.UNSUBSCRIBE_LITERAL, MqttTopic.getDeviceTopic(device), true));
		ACLs.forEach(ACL -> {
			publisher.addCommand(role.addRoleACLCommand(ACL));
		});
		publisher.publish();
	}
	
	public void delete(Device device) {
		DynSecRole role = new DynSecRole("role_" + device.getUser().getEmail().getAddress());
		List<DynSecACL> ACLs = new ArrayList<>();
		ACLs.add(new DynSecACL(ACLType.PUBLISH_CLIENT_SEND, MqttTopic.getDeviceTopicToPersit(device), true));
		ACLs.add(new DynSecACL(ACLType.PUBLISH_CLIENT_SEND, MqttTopic.getDeviceTopic(device), true));
		ACLs.add(new DynSecACL(ACLType.SUBSCRIBE_LITERAL, MqttTopic.getDeviceTopic(device), true));
		ACLs.add(new DynSecACL(ACLType.UNSUBSCRIBE_LITERAL, MqttTopic.getDeviceTopic(device), true));
		ACLs.forEach(ACL -> {
			publisher.addCommand(role.removeRoleACLCommand(ACL));
		});
		publisher.publish();
	}
}
