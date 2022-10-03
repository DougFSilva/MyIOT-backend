package br.com.MyIot.exception.handler;

import java.io.Serializable;
/**
 * A classe <b>StandartError</b> representa um padrão de erro para ser encaminhado como resposta a uma solicitação http
 * na ocorr~encia de uma Exception capturada pela classe <b>ExceptionHanlder</b>
 * @since Out 2022
 * @version 1.0
 */
public class StandartError implements Serializable {

	private static final long serialVersionUID = 1L;

	private long timestamp;
	private Integer status;
	private String error;

	public StandartError() {
		super();

	}

	public StandartError(long timestamp, Integer status, String error) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
