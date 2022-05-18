package com.magenic.mobog.exercise2app.controllers;

import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.requests.AddAuthorRequest;
import com.magenic.mobog.exercise2app.responses.AuthorResponse;
import com.magenic.mobog.exercise2app.responses.BookResponse;
import com.magenic.mobog.exercise2app.responses.PageResponse;
import com.magenic.mobog.exercise2app.services.AuthorService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	private AuthorService service;
	
	public AuthorController(AuthorService authorService) {
		this.service = authorService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{authorId}/books")
    BookResponse addBook(@PathVariable Long authorId, @RequestBody AddBookRequest newBook) {
		return service.addBookToAuthor(authorId, newBook);
	}
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	AuthorResponse addAuthor(@RequestBody AddAuthorRequest request){
		return service.createAuthor(request);
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path ="/{authorId}/books")
	PageResponse<BookResponse> getAllBooks(@PathVariable("authorId") Long authorId, @PageableDefault(value = 10, page = 0) Pageable p){
		return service.findBookByAuthorWithPage(authorId, p);
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	PageResponse<AuthorResponse> getAllAuthors(@PageableDefault(value = 10, page = 0) Pageable p){
		return service.findAuthorsWithPage(p);
	}
}
