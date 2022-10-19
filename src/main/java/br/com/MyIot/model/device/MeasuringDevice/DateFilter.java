package br.com.MyIot.model.device.MeasuringDevice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateFilter {

	private LocalDate initialDate;

	private LocalDate finalDate;

	private LocalTime initialTime;

	private LocalTime finalTime;

	public DateFilter() {
		super();
	}

	public DateFilter(String initialDate, String finalDate, String initialTime, String finalTime) {
		this.initialDate = LocalDate.parse(initialDate);
		this.finalDate = LocalDate.parse(finalDate);
		if(initialTime.equals("MIN")) {
			this.initialTime = LocalTime.MIN;
		}else {
			this.initialTime = LocalTime.parse(initialTime);
		}
		if(finalTime.equals("MAX")) {
			this.finalTime = LocalTime.MAX;
		}else {
			this.finalTime = LocalTime.parse(finalTime);
		}
		
	}

	public LocalDateTime getInitialDateTime() {
		return LocalDateTime.of(initialDate, initialTime);

	}

	public LocalDateTime getFinalDateTime() {
		return LocalDateTime.of(this.finalDate, this.finalTime);
	}

	public LocalDate getInitialDate() {
		return initialDate;
	}

	public LocalDate getFinalDate() {
		return finalDate;
	}

	public LocalTime getInitialTime() {
		return initialTime;
	}

	public LocalTime getFinalTime() {
		return finalTime;
	}

	@Override
	public String toString() {
		return "DateFilter [initialDate=" + initialDate + ", finalDate=" + finalDate + ", initialTime=" + initialTime
				+ ", finalTime=" + finalTime + "]";
	}

}
