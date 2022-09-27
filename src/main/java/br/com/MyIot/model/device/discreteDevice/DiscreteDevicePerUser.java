package br.com.MyIot.model.device.discreteDevice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.MyIot.exception.UserDevicesLimitExceededException;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;

@Service
public class DiscreteDevicePerUser {

	@Value("${user.admin.max.discreteDevice}")
	private Integer maxDevicesAdminUser;
	
	@Value("${user.gold.max.discreteDevice}")
	private Integer maxDevicesGoldUser;
	
	@Value("${user.silver.max.discreteDevice}")
	private Integer maxDevicesOfSilverUser;
	
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
