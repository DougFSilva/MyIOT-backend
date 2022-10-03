package br.com.MyIot.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import br.com.MyIot.exception.DataIntegratyViolationException;
import br.com.MyIot.exception.FileReadErrorException;
import br.com.MyIot.exception.InvalidEmailException;
import br.com.MyIot.exception.InvalidPasswordException;
import br.com.MyIot.exception.MqttFailException;
import br.com.MyIot.exception.ObjectNotFoundException;
import br.com.MyIot.exception.OperationNotAllowedException;
import br.com.MyIot.exception.UnknownHostException;
import br.com.MyIot.exception.UserDevicesLimitExceededException;
import br.com.MyIot.exception.UserNotApprovedException;

/**
 * A classe <b>ExceptionHandler</b> é uma classe controller que captura as exceções do sistema e retorna essa exceção como
 * resposta da requisição http.
 * @since Out 2022
 * @version 1.0
 */
@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<StandartError> dataIntegratyViolationException(DataIntegratyViolationException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFoundException(ObjectNotFoundException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UnknownHostException.class)
	public ResponseEntity<StandartError> unknownHostException(UnknownHostException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<StandartError> invalidEmailException(InvalidEmailException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<StandartError> invalidPasswordException(InvalidPasswordException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UserDevicesLimitExceededException.class)
	public ResponseEntity<StandartError> userDevicesLimitExceededException(
			UserDevicesLimitExceededException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandartError> illegalArgumentException(IllegalArgumentException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(FileReadErrorException.class)
	public ResponseEntity<StandartError> fileReadErrorException(FileReadErrorException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UserNotApprovedException.class)
	public ResponseEntity<StandartError> userNotApprovedException(UserNotApprovedException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(MqttFailException.class)
	public ResponseEntity<StandartError> mqttFailException(MqttFailException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(OperationNotAllowedException.class)
	public ResponseEntity<StandartError> operationNotAllowedException(OperationNotAllowedException exception) {
		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
