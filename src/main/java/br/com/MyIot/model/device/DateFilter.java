package br.com.MyIot.model.device;

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
		this.initialTime = LocalTime.parse(initialTime);
		this.finalTime = LocalTime.parse(finalTime);
	}

	public DateFilter(String initialDate, String finalDate) {
		this.initialDate = LocalDate.parse(initialDate);
		this.finalDate = LocalDate.parse(finalDate);
	}

	public DateFilter(String date) {
		this.initialDate = LocalDate.parse(date);
	}

	public DateFilter(String date, String initialTime, String finalTime) {
		this.initialDate = LocalDate.parse(date);
		this.finalDate = LocalDate.parse(date);
		this.initialTime = LocalTime.parse(initialTime);
		this.finalTime = LocalTime.parse(finalTime);
	}

	public LocalDateTime getInitialDateTime() {
		if (this.initialTime == null) {
			return LocalDateTime.of(this.initialDate, LocalTime.MIN);
		}
		return LocalDateTime.of(initialDate, initialTime);

	}

	public LocalDateTime getFinalDateTime() {
		if (this.finalDate == null) {
			this.finalDate = this.initialDate;
		}
		if (this.finalTime == null) {
			return LocalDateTime.of(finalDate, LocalTime.MAX);
		}
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
