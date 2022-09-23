package br.com.MyIot.dto;

public abstract class DeviceDto {

	protected String id;

	protected String userId;

	protected String location;

	protected String name;
	
	protected DeviceDto() {}

	public DeviceDto(String id, String userId, String location, String name) {
		this.id = id;
		this.userId = userId;
		this.location = location;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

}
