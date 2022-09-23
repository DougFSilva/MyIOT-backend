package br.com.MyIot.model.device;

import br.com.MyIot.exception.UserDevicesLimitExceededException;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;

public class DevicesPerUserValidate {

	private Integer maxDevicesAdminUser = 100;
	private Integer maxDevicesGoldUser = 10;
	private Integer maxDevicesOfSilverUser = 5;
	
	public DevicesPerUserValidate(Integer maxDevicesAdminUser, Integer maxDevicesGoldUser, Integer maxDevicesOfSilverUser) {
		this.maxDevicesAdminUser = maxDevicesAdminUser;
		this.maxDevicesGoldUser = maxDevicesGoldUser;
		this.maxDevicesOfSilverUser = maxDevicesOfSilverUser;
	}

	public boolean validate(User user, Integer devicesCount) {
		ProfileType profileType = user.getProfiles().stream()
				.sorted((p1, p2) -> Long.compare(p1.getType().getCod(), p2.getType().getCod())).findFirst().get()
				.getType();
		if ((profileType == ProfileType.ADMIN) && (devicesCount <= maxDevicesAdminUser)) {
			return true;
		} else if ((profileType == ProfileType.GOLD_USER) && (devicesCount < maxDevicesGoldUser)) {
			return true;
		} else if ((profileType == ProfileType.SILVER_USER) && (devicesCount < maxDevicesOfSilverUser)) {
			return true;
		} else {
			throw new UserDevicesLimitExceededException("Limit of devices per user exceeded!");
		}
	}
}
