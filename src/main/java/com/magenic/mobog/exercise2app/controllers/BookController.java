package com.magenic.mobog.exercise2app.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.responses.GetBookResponse;
import com.magenic.mobog.exercise2app.services.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	private BookService service;
	
	public BookController(BookService service) {
		this.service = service;
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	GetBookResponse addBook(@RequestBody AddBookRequest newBook) {
		return service.save(newBook);
	}
	@GetMapping
	List<GetBookResponse> getAllBooks(){
		return service.findAll();
	}
}
