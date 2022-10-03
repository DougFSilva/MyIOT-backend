package br.com.MyIot.model.device.MeasuringDevice;

import org.springframework.stereotype.Service;

import br.com.MyIot.exception.UserDevicesLimitExceededException;
import br.com.MyIot.model.user.ProfileType;
import br.com.MyIot.model.user.User;

/**
 * A classe <b>MeasuringDevicePerUser</b> faz a validação da quantidade de dispositivos do tipo <b>MeasuringDevice</b> permitido para cada
 * usuário com perfil admin, gold ou silver 
 * @author Douglas Ferreira da Silva
 * @since Out 2022
 * @version 1.0
 */
@Service
public class MeasuringDevicePerUser {

	public boolean validate(User user, Integer devicesCount) {
		ProfileType profileType = user.getProfiles().stream()
				.sorted((p1, p2) -> Long.compare(p1.getType().getCod(), p2.getType().getCod())).findFirst().get()
				.getType();
		if ((profileType == ProfileType.ADMIN) && (devicesCount <= 25)) {
			return true;
		} else if ((profileType == ProfileType.GOLD_USER) && (devicesCount < 12)) {
			return true;
		} else if ((profileType == ProfileType.SILVER_USER) && (devicesCount < 6)) {
			return true;
		} else {
			throw new UserDevicesLimitExceededException("Limit of devices per user exceeded!");
		}
	}
}
