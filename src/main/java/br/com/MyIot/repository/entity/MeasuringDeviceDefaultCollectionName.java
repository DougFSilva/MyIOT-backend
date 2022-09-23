package br.com.MyIot.repository.entity;

import org.springframework.stereotype.Service;

@Service
public class MeasuringDeviceDefaultCollectionName {


	public MeasuringDeviceDefaultCollectionName() {
	}

	public String getName(String deviceId) {
		return "device_" + deviceId;
	}

}
