package br.com.MyIot.model.device.analogOutputDevice;

import org.springframework.stereotype.Service;

import br.com.MyIot.exception.UserDevicesLimitExceededException;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;

@Service
public class AnalogOutputDevicePerUser {
	
	public boolean validate(User user, Integer devicesCount) {
		ProfileType profileType = user.getProfiles().stream()
				.sorted((p1, p2) -> Long.compare(p1.getType().getCod(), p2.getType().getCod())).findFirst().get()
				.getType();
		if ((profileType == ProfileType.ADMIN) && (devicesCount <= 20)) {
			return true;
		} else if ((profileType == ProfileType.GOLD_USER) && (devicesCount < 8)) {
			return true;
		} else if ((profileType == ProfileType.SILVER_USER) && (devicesCount < 4)) {
			return true;
		} else {
			throw new UserDevicesLimitExceededException("Limit of devices per user exceeded!");
		}
	}
}
