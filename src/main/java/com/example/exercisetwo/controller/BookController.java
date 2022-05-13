package com.example.exercisetwo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.exercisetwo.exception.RecordNotFoundException;
import com.example.exercisetwo.model.Book;
import com.example.exercisetwo.service.BookService;


@Controller
@RequestMapping(value = "/books")
public class BookController {
	
	@Autowired
	BookService service;
	
	@PostMapping
	public ResponseEntity<Book> saveBook(@RequestBody Book book) throws RecordNotFoundException {
		Book newBook = service.saveBook(book);
		
		return new ResponseEntity<Book>(newBook, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = service.getBooks();
		
		return new ResponseEntity<List<Book>>(books, new HttpHeaders(), HttpStatus.OK);
	}

}
