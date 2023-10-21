package ru.berkovets.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ru.berkovets.springcourse.dao.BookDAO;
import ru.berkovets.springcourse.dao.PersonDAO;
import ru.berkovets.springcourse.models.Book;
import ru.berkovets.springcourse.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController {

	@Autowired
	private final BookDAO bookDAO;
	private final PersonDAO personDAO;

	public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
		this.bookDAO = bookDAO;
		this.personDAO = personDAO;
	}

	@GetMapping()
	public String index(Model model) {
		model.addAttribute("books", bookDAO.getAll());
		return "books/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
		model.addAttribute("book", bookDAO.getById(id));
		Person owner = bookDAO.getOwnerById(id);
		
		if (owner == null) {
			model.addAttribute("people", personDAO.getAll());
		} else {
			model.addAttribute("owner", owner);
		}
		
		return "books/show";
	}

	@PatchMapping("/{id}/occupy")
	public String occupy(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
		bookDAO.setPersonId(id, person.getId());

		return "redirect:/books/{id}";
	}

	@PatchMapping("/{id}/free")
	public String free(@PathVariable("id") int id) {
		bookDAO.freePersonId(id);

		return "redirect:/books/{id}";
	}

	@GetMapping("/new")
	public String newBook(@ModelAttribute("book") Book book) {
		return "books/new";
	}

	@PostMapping()
	public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "books/new";
		}

		bookDAO.create(book);
		return "redirect:/books";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable("id") int id, Model model) {
		model.addAttribute("book", bookDAO.getById(id));
		return "books/edit";
	}

	@PatchMapping("/{id}")
	public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
			@PathVariable("id") int id) {
		if (bindingResult.hasErrors()) {
			return "book/edit";
		}

		bookDAO.update(id, book);
		return "redirect:/books/{id}";
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		bookDAO.delete(id);
		return "redirect:/books";
	}
}
