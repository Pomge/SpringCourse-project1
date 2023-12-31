package ru.berkovets.springcourse.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.berkovets.springcourse.models.Book;
import ru.berkovets.springcourse.models.Person;

@Component
public class PersonDAO {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public PersonDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Person> getAll() {
		return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<Person>(Person.class));
	}
	
	public List<Book> getBooksByPersonId(int id) {
		String sql = "SELECT * FROM Book WHERE person_id=?";
		return jdbcTemplate.query(sql, new Object[] { id },
				new BeanPropertyRowMapper<Book>(Book.class));
	}

	public Person getByFIO(String fio) {
		String sql = "SELECT * FROM Person WHERE fio=?";
		return (Person) jdbcTemplate.query(sql, new Object[] { fio },
				new BeanPropertyRowMapper<Person>(Person.class)).stream().findAny().orElse(null);
	}

	public Person getById(Integer id) {
		String sql = "SELECT * FROM Person WHERE id=?";
		return (Person) jdbcTemplate
				.query(sql, new Object[] { id }, new BeanPropertyRowMapper<Person>(Person.class))
				.stream().findAny().orElse(null);
	}

	public void create(Person person) {
		jdbcTemplate.update("INSERT INTO Person(fio, year) VALUES(?, ?)", person.getFIO(), person.getYear());
	}

	public void update(int id, Person person) {
		jdbcTemplate.update("UPDATE Person SET fio=?, year=? WHERE id=?", person.getFIO(), person.getYear(),
				person.getId());
	}

	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
	}
}
