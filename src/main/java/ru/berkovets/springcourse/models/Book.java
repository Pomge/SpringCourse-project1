package ru.berkovets.springcourse.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Book {
	private int id;
	private Integer person_id;

	@NotEmpty(message = "Book name should not be empty")
	private String name;

	@NotEmpty(message = "Book author should not be empty")
	private String author;

	@Min(value = 0, message = "Year should be greater than 0")
	@Max(value = 2023, message = "Year should be less than 2023")
	private int year;

	public Book() {

	}

	public Book(int id, Integer person_id, String name, String author, int year) {
		super();
		this.id = id;
		this.person_id = person_id;
		this.name = name;
		this.author = author;
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
