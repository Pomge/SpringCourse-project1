package ru.berkovets.springcourse.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Person {
	private int id;

	@NotEmpty(message = "FIO should not be empty")
	private String FIO;

	@Min(value = 1900, message = "Born year should be greater than 1900")
	@Max(value = 2005, message = "Born year should be less than 2005")
	private int year;

	public Person() {

	}

	public Person(int id, String FIO, int year) {
		super();
		this.id = id;
		this.FIO = FIO;
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFIO() {
		return FIO;
	}

	public void setFIO(String fIO) {
		FIO = fIO;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
