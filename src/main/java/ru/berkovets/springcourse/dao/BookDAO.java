package ru.berkovets.springcourse.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.berkovets.springcourse.models.Book;
import ru.berkovets.springcourse.models.Person;

@Component
public class BookDAO {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public BookDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Book> getAll() {
		BeanPropertyRowMapper<Book> beanPropertyRowMapper = new BeanPropertyRowMapper<Book>(Book.class);
		beanPropertyRowMapper.setPrimitivesDefaultedForNullValue(true);
		return jdbcTemplate.query("SELECT * FROM Book", beanPropertyRowMapper);
	}

	public Book getById(int id) {
		BeanPropertyRowMapper<Book> beanPropertyRowMapper = new BeanPropertyRowMapper<Book>(Book.class);
		beanPropertyRowMapper.setPrimitivesDefaultedForNullValue(true);
		String sql = "SELECT * FROM Book WHERE id=?";
		return (Book) jdbcTemplate.query(sql, new Object[] { id }, beanPropertyRowMapper).stream().findAny()
				.orElse(null);
	}

	public Person getOwnerById(int id) {
		BeanPropertyRowMapper<Person> beanPropertyRowMapper = new BeanPropertyRowMapper<Person>(Person.class);
		beanPropertyRowMapper.setPrimitivesDefaultedForNullValue(true);
		String sql = "SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id=?";
		return (Person) jdbcTemplate.query(sql, new Object[] { id }, beanPropertyRowMapper).stream().findAny()
				.orElse(null);
	}

	public void create(Book book) {
		jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(),
				book.getYear());
	}

	public void update(int id, Book book) {
		jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE id=?", book.getName(), book.getAuthor(),
				book.getYear(), id);
	}

	public void freePersonId(int id) {
		jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE id=?", id);
	}

	public void setPersonId(int id, Integer person_id) {
		jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", person_id, id);
	}

	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
	}
}
