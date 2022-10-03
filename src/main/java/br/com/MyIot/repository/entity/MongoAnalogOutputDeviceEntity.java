package br.com.MyIot.repository.entity;

import org.bson.types.ObjectId;

/**
 * A classe <b>MongoAnalogOutputDeviceEntity</b> é uma entidade que representa um objeto do tipo <b>MongoAnalogOutputDevice</b> para ser 
 * persistido no banco de dados mongoDb
 * @since Out 2022
 * @version 1.0
 */
public class MongoAnalogOutputDeviceEntity {

	private ObjectId id;

	private ObjectId userId;

	private String location;

	private String name;
	
	private Integer output;

	public MongoAnalogOutputDeviceEntity(ObjectId id, ObjectId userId, String location, String name, Integer output) {
		this.id = id;
		this.userId = userId;
		this.location = location;
		this.name = name;
		this.output = output;
	}

	public ObjectId getId() {
		return id;
	}

	public ObjectId getUserId() {
		return userId;
	}
	
	public MongoAnalogOutputDeviceEntity generateId() {
		this.id = new ObjectId();
		return this;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public Integer getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return "MongoAnalogOutputDeviceEntity [id=" + id + ", userId=" + userId + ", location=" + location + ", name="
				+ name + ", output=" + output + "]";
	}
	
	
	
}
