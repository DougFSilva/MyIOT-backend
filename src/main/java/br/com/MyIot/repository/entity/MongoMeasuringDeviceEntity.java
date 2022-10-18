package br.com.MyIot.repository.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

/**
 * A classe <b>MongoMeasuringDeviceEntity</b> é uma entidade que representa um objeto do tipo <b>MongoMeasuringDevice</b> para ser 
 * persistido no banco de dados mongoDb
 * @since Out 2022
 * @version 1.0
 */
public class MongoMeasuringDeviceEntity {

	private ObjectId id;

	private ObjectId userId;

	private String location;

	private String name;

	private List<String> keyNames = new ArrayList<>();

	public MongoMeasuringDeviceEntity(ObjectId userId, String location, String name, List<String> keyNames) {
		this.userId = userId;
		this.location = location;
		this.name = name;
		this.keyNames = keyNames;
	}

	public MongoMeasuringDeviceEntity(ObjectId id, ObjectId userId, String location, String name,
			List<String> keyNames) {
		super();
		this.id = id;
		this.userId = userId;
		this.location = location;
		this.name = name;
		this.keyNames = keyNames;
	}

	public ObjectId getId() {
		return id;
	}
	
	public MongoMeasuringDeviceEntity generateId() {
		this.id = new ObjectId();
		return this;
	}

	public ObjectId getUserId() {
		return userId;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public List<String> getKeyNames() {
		return keyNames;
	}

	@Override
	public String toString() {
		return "MongoMeasuringDeviceEntity [id=" + id + ", userId=" + userId + ", location=" + location + ", name="
				+ name + ", keyNames=" + keyNames + "]";
	}
	

}