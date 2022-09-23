package br.com.MyIot.mqtt;

import java.io.FileInputStream;
import java.util.Properties;

import br.com.MyIot.exception.FileReadErrorException;

public class MqttProperties {
	
	private String uri;

	private String adminClientId;

	private String adminUsername;

	private String adminPassword;

	public MqttProperties() {
		Properties properties = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream("src/main/resources/application.properties");
			properties.load(file);
		} catch (Exception e) {
			throw new FileReadErrorException(e.getMessage());
		}
		this.uri = properties.getProperty("mqtt.uri");
		adminUsername = properties.getProperty("mqtt.admin.username");
		adminPassword = properties.getProperty("mqtt.admin.password");
		adminClientId = properties.getProperty("mqtt.admin.clientId");
	}
	
	public String getUri() {
		return uri;
	}

	public String getAdminClientId() {
		return adminClientId;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

}
