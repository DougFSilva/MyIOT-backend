package br.com.MyIot.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;

import br.com.MyIot.model.device.MeasuringDevice.MeasuredValue;
import br.com.MyIot.model.device.MeasuringDevice.MeasuredValueRepository;
import br.com.MyIot.model.device.MeasuringDevice.MeasuringDevice;
import br.com.MyIot.repository.codec.MeasuredValueCodec;
import br.com.MyIot.repository.config.MongoConnection;
import br.com.MyIot.repository.converter.MongoMeasuredValueConverter;
import br.com.MyIot.repository.entity.MongoMeasuredValueEntity;

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
	public void deleteByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime, LocalDateTime finaldateTime) {
		getCollection(device.getId()).deleteMany(
				Filters.and(Filters.gte("timestamp", initialDateTime), Filters.lte("timestamp", finaldateTime)));
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
	public List<MeasuredValue> findAllByDevice(MeasuringDevice device) {
		List<MeasuredValue> measuredValues = new ArrayList<>();
		MongoCursor<MongoMeasuredValueEntity> mongoCursor = getCollection(device.getId())
				.find((Filters.eq("deviceId", new ObjectId(device.getId())))).batchSize(20000).iterator();
		mongoConnection.close();
		mongoCursor.forEachRemaining(cursor -> measuredValues.add(measuredConverter.toMeasuredValue(device, cursor)));
		return measuredValues;
	}

	@Override
	public List<MeasuredValue> findAllByTimeRange(MeasuringDevice device, LocalDateTime initialDateTime,
			LocalDateTime finalDateTime) {
		List<MeasuredValue> measuredValues = new ArrayList<>();
		MongoCursor<MongoMeasuredValueEntity> mongoCursor = getCollection(device.getId())
				.find(Filters.and(Filters.gte("timestamp", initialDateTime), Filters.lte("timestamp", finalDateTime)))
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

}
