package br.com.MyIot.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;

import br.com.MyIot.model.device.MeasuringDevice.DateFilter;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValue;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValueRepository;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.repository.codec.MeasuredValueCodec;
import br.com.MyIot.repository.config.MongoConnection;
import br.com.MyIot.repository.converter.MongoMeasuredValueConverter;
import br.com.MyIot.repository.entity.MongoMeasuredValueEntity;

/**
 * A classe <b>MongoMeasuredValueRepository</b> é uma classe repository que
 * implementa a interface <b>MeasuredValueRepository</b> e faz a persistência no
 * banco de dados MongoDb
 * 
 * @since Out 2022
 * @version 1.0
 */
@Repository
public class MongoMeasuredValueRepository implements MeasuredValueRepository {

	@Autowired
	private MongoConnection mongoConnection;

	@Autowired
	private MongoMeasuredValueConverter measuredConverter;

	@Override
	public String create(MeasuredValue measuredValue) {
		InsertOneResult result = getCollection(measuredValue.getDevice().getId())
				.insertOne(measuredConverter.toEntity(measuredValue));
		mongoConnection.close();
		return result.getInsertedId().asObjectId().getValue().toHexString();
	}

	@Override
	public void deleteById(MeasuringDevice device, String id) {
		getCollection(device.getId()).deleteOne(Filters.eq(new ObjectId(id)));
		mongoConnection.close();
		return;
	}

	@Override
	public void deleteByTimeRange(MeasuringDevice device, DateFilter filter) {
		getCollection(device.getId()).deleteMany(
				Filters.and(Filters.gte("timestamp", filter.getInitialDateTime()), 
							Filters.lte("timestamp", filter.getFinalDateTime())));
		mongoConnection.close();
		return;
	}

	@Override
	public void deleteAllByDevice(MeasuringDevice device) {
		getCollection(device.getId()).deleteMany(Filters.eq("deviceId", new ObjectId(device.getId())));
		mongoConnection.close();
		return;
	}

	@Override
	public List<MeasuredValue> findAllByDevice(MeasuringDevice device, Integer limit) {
		List<MeasuredValue> measuredValues = new ArrayList<>();
		MongoCursor<MongoMeasuredValueEntity> mongoCursor = getCollection(device.getId())
				.find((Filters.eq("deviceId", new ObjectId(device.getId()))))
				.batchSize(20000)
				.sort(Sorts.descending("timestamp"))
				.limit(limit)
				.iterator();
		mongoConnection.close();
		mongoCursor.forEachRemaining(cursor -> measuredValues.add(measuredConverter.toMeasuredValue(device, cursor)));
		measuredValues.sort(((a, b) -> a.getTimestamp().compareTo(b.getTimestamp())));
		return measuredValues;
	}

	@Override
	public Map<MeasuringDevice, List<MeasuredValue>> findAllByDevices(List<MeasuringDevice> devices, Integer limit) {
		MongoDatabase db = getDatabase();
		Map<MeasuringDevice, List<MeasuredValue>> map = new HashMap<>();
		devices.forEach(device -> {
			MongoCursor<MongoMeasuredValueEntity> mongoCursor = db
					.getCollection("device_" + device.getId(), MongoMeasuredValueEntity.class)
					.find((Filters.eq("deviceId", new ObjectId(device.getId()))))
					.sort(Sorts.descending("timestamp"))
					.limit(limit)
					.batchSize(20000)
					.iterator();
			List<MeasuredValue> measuredValues = new ArrayList<>();
			mongoCursor.forEachRemaining(cursor -> measuredValues.add(measuredConverter.toMeasuredValue(device, cursor)));
			measuredValues.sort(((a, b) -> a.getTimestamp().compareTo(b.getTimestamp())));
			map.put(device, measuredValues);
		});
		mongoConnection.close();
		return map;
	}
	
	@Override
	public Map<MeasuringDevice, List<MeasuredValue>> findAllByDevicesAndTimeRange(List<MeasuringDevice> devices, 
			DateFilter filter, Integer limit) {
		MongoDatabase db = getDatabase();
		Map<MeasuringDevice, List<MeasuredValue>> map = new HashMap<>();
		devices.forEach(device -> {
			MongoCursor<MongoMeasuredValueEntity> mongoCursor = db
					.getCollection("device_" + device.getId(), MongoMeasuredValueEntity.class)
					.find(Filters.and(
							Filters.eq("deviceId", new ObjectId(device.getId())), 
							Filters.gte("timestamp", filter.getInitialDateTime()), 
							Filters.lte("timestamp", filter.getFinalDateTime())))
					.sort(Sorts.descending("timestamp"))
					.limit(limit)
					.batchSize(20000)
					.iterator();
			List<MeasuredValue> measuredValues = new ArrayList<>();
			mongoCursor.forEachRemaining(cursor -> measuredValues.add(measuredConverter.toMeasuredValue(device, cursor)));
			measuredValues.sort(((a, b) -> a.getTimestamp().compareTo(b.getTimestamp())));
			map.put(device, measuredValues);
		});
		mongoConnection.close();
		return map;
	}

	@Override
	public List<MeasuredValue> findAllByDeviceAndTimeRange(MeasuringDevice device, DateFilter filter, Integer limit) {
		List<MeasuredValue> measuredValues = new ArrayList<>();
		MongoCursor<MongoMeasuredValueEntity> mongoCursor = getCollection(device.getId())
				.find(Filters.and(
						Filters.gte("timestamp", filter.getInitialDateTime()), 
						Filters.lte("timestamp", filter.getFinalDateTime())))
				.limit(limit)
				.iterator();
		mongoConnection.close();
		mongoCursor.forEachRemaining(
				measuredValue -> measuredValues.add(measuredConverter.toMeasuredValue(device, measuredValue)));
		return measuredValues;
	}

	private MongoCollection<MongoMeasuredValueEntity> getCollection(String collectionName) {
		return mongoConnection.connect(new MeasuredValueCodec()).getDatabase().getCollection("device_" + collectionName,
				MongoMeasuredValueEntity.class);
	}

	private MongoDatabase getDatabase() {
		return mongoConnection.connect(new MeasuredValueCodec()).getDatabase();
	}


}