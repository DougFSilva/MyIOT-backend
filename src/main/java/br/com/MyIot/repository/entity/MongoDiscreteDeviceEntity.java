package br.com.MyIot.repository.entity;

import org.bson.types.ObjectId;

/**
 * A classe <b>MongoDiscreteDeviceEntity</b> é uma entidade que representa um objeto do tipo <b>MongoDiscreteDevice</b> para ser 
 * persistido no banco de dados mongoDb
 * @since Out 2022
 * @version 1.0
 */
public class MongoDiscreteDeviceEntity {

	private ObjectId id;

	private ObjectId userId;

	private String location;

	private String name;

	private boolean status;

	public MongoDiscreteDeviceEntity() {
		super();
	}

	public MongoDiscreteDeviceEntity(ObjectId id, ObjectId userId, String location, String name, boolean status) {
		this.id = id;
		this.userId = userId;
		this.location = location;
		this.name = name;
		this.status = status;
	}
	
	public MongoDiscreteDeviceEntity generateId() {
		this.id = new ObjectId();
		return this;
	}

	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MongoDiscreteDeviceEntity [id=" + id + ", userId=" + userId + ", location=" + location + ", name="
				+ name + ", status=" + status + "]";
	}

}
