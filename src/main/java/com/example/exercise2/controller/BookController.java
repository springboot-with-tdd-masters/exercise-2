package com.example.exercise2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exercise2.entity.BookEntity;
import com.example.exercise2.exception.RecordNotFoundException;
import com.example.exercise2.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping()
	public List<BookEntity> getAllBooks() throws RecordNotFoundException {
		return bookService.findAll();
	}

	@PostMapping
	public BookEntity saveBook(BookEntity book) throws RecordNotFoundException {
		return bookService.saveBook(book);
	}
}
