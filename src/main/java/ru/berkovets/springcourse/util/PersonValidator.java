package ru.berkovets.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.berkovets.springcourse.dao.PersonDAO;
import ru.berkovets.springcourse.models.Person;

@Component
public class PersonValidator implements Validator {

	private final PersonDAO personDAO;

	@Autowired
	public PersonValidator(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;

		Person personFromDataBase = personDAO.getByFIO(person.getFIO());
		if (personFromDataBase != null && (person.getId() != personFromDataBase.getId())) {
			errors.rejectValue("FIO", "", "This fio is already exist");
		}
	}

}
