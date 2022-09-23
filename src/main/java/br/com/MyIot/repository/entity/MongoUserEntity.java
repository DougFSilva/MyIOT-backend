package br.com.MyIot.repository.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import br.com.MyIot.repository.MongoProfile;

public class MongoUserEntity {

	private ObjectId id;

	private String email;

	private String name;

	private String password;

	private String clientMqttPassword;

	private boolean approvedRegistration;

	private List<MongoProfile> profiles = new ArrayList<>();

	public MongoUserEntity() {
		super();
	}

	public MongoUserEntity(ObjectId id, String email, String name, String password, String clientMqttPassword,
			boolean approvedRegistration, List<MongoProfile> mongoProfiles) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.clientMqttPassword = clientMqttPassword;
		this.approvedRegistration = approvedRegistration;
		this.profiles = mongoProfiles;
	}

	public ObjectId getId() {
		return id;
	}

	public MongoUserEntity generateId() {
		this.id = new ObjectId();
		return this;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getClientMqttPassword() {
		return clientMqttPassword;
	}

	public boolean isApporvedRegistration() {
		return approvedRegistration;
	}

	public List<MongoProfile> getProfiles() {
		return profiles;
	}

	public String getPassword() {
		return this.password;
	}

	@Override
	public String toString() {
		return "MongoUserEntity [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password
				+ ", clientMqttPassword=" + clientMqttPassword + ", approvedRegistration=" + approvedRegistration
				+ ", profiles=" + profiles + "]";
	}

}
