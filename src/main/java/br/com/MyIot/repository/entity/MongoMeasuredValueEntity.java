package br.com.MyIot.repository.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;

/**
 * A classe <b>MongoMeasuredValueEntity</b> é uma entidade que representa um objeto do tipo <b>MongoMeasuredValue</b> para ser 
 * persistido no banco de dados mongoDb
 * @since Out 2022
 * @version 1.0
 */
public class MongoMeasuredValueEntity {
	
	private ObjectId id;

	private ObjectId deviceId;

	private List<Double> values;

	private LocalDateTime timeStamp;

	public MongoMeasuredValueEntity(ObjectId id, ObjectId deviceId, List<Double> values, LocalDateTime timeStamp) {
		this.id = id;
		this.deviceId = deviceId;
		this.values = values;
		this.timeStamp = timeStamp;
	}
	
	public MongoMeasuredValueEntity(ObjectId deviceId, List<Double> values, LocalDateTime timeStamp) {
		this.deviceId = deviceId;
		this.values = values;
		this.timeStamp = timeStamp;
	}

	public ObjectId getId() {
		return id;
	}

	public MongoMeasuredValueEntity generateId() {
		this.id = new ObjectId();
		return this;
	}

	public ObjectId getDeviceId() {
		return deviceId;
	}

	public List<Double> getValues() {
		return values;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	@Override
	public String toString() {
		return "MongoMeasuredValueEntity [deviceId=" + deviceId + ", values=" + values + ", timeStamp=" + timeStamp
				+ "]";
	}

}
