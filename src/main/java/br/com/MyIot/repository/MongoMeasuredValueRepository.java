package br.com.MyIot.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;

import br.com.MyIot.model.device.MeasuredValue;
import br.com.MyIot.model.device.MeasuredValueRepository;
import br.com.MyIot.model.device.MeasuringDevice;
import br.com.MyIot.repository.codec.MeasuredValueCodec;
import br.com.MyIot.repository.config.MongoConnection;
import br.com.MyIot.repository.converter.MongoMeasuredValueConverter;
import br.com.MyIot.repository.entity.MeasuringDeviceDefaultCollectionName;
import br.com.MyIot.repository.entity.MongoMeasuredValueEntity;

@Repository
public class MongoMeasuredValueRepository implements MeasuredValueRepository {

	@Autowired
	private MongoConnection mongoConnection;

	@Autowired
	private MongoMeasuredValueConverter measuredConverter;

	@Autowired
	private MeasuringDeviceDefaultCollectionName collectionName;

	@Override
	public String create(MeasuredValue measuredValue) {
		MongoClient client = getClient();
		InsertOneResult result = getCollection(client, measuredValue.getDevice().getId())
				.insertOne(measuredConverter.toEntity(measuredValue));
		client.close();
		return result.getInsertedId().asObjectId().getValue().toHexString();
	}

	@Override
	public void deleteById(MeasuringDevice device, String id) {
		MongoClient client = getClient();
		getCollection(client, device.getId()).deleteOne(Filters.eq(new ObjectId(id)));
		client.close();
		return;
	}

	@Override
	public void deleteByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime, LocalDateTime finaldateTime) {
		MongoClient client = getClient();
		getCollection(client, device.getId()).deleteMany(
				Filters.and(Filters.gte("timestamp", initialDateTime), Filters.lte("timestamp", finaldateTime)));
		client.close();
		return;
	}

	@Override
	public void deleteAllByDevice(MeasuringDevice device) {
		MongoClient client = getClient();
		getCollection(client, device.getId()).deleteMany(Filters.eq("deviceId", new ObjectId(device.getId())));
		client.close();
		return;
	}

	@Override
	public List<MeasuredValue> findAllByDevice(MeasuringDevice device) {
		MongoClient client = getClient();
		List<MeasuredValue> measuredValues = new ArrayList<>();
		MongoCursor<MongoMeasuredValueEntity> mongoCursor = getCollection(client, device.getId())
				.find((Filters.eq("deviceId", new ObjectId(device.getId())))).iterator();
		client.close();
		mongoCursor.forEachRemaining(cursor -> measuredValues.add(measuredConverter.toMeasuredValue(device, cursor)));
		return measuredValues;
	}

	@Override
	public List<MeasuredValue> findAllByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime,
			LocalDateTime finalDateTime) {
		MongoClient client = getClient();
		List<MeasuredValue> measuredValues = new ArrayList<>();
		MongoCursor<MongoMeasuredValueEntity> mongoCursor = getCollection(client, device.getId())
				.find(Filters.and(Filters.gte("timestamp", initialDateTime), Filters.lte("timestamp", finalDateTime)))
				.iterator();
		client.close();
		mongoCursor.forEachRemaining(
				measuredValue -> measuredValues.add(measuredConverter.toMeasuredValue(device, measuredValue)));
		return measuredValues;
	}

	private MongoClient getClient() {
		return mongoConnection.getClient(new MeasuredValueCodec());
	}

	private MongoCollection<MongoMeasuredValueEntity> getCollection(MongoClient client, String deviceId) {
		String collectionName = this.collectionName.getName(deviceId);
		return mongoConnection.getDatabase(client).getCollection(collectionName, MongoMeasuredValueEntity.class);
	}

}
