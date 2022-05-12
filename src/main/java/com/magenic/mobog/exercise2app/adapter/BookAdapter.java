package com.magenic.mobog.exercise2app.adapter;

import java.util.Optional;

import com.magenic.mobog.exercise2app.entities.Book;
import com.magenic.mobog.exercise2app.exceptions.InvalidBookRequestException;
import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.responses.GetBookResponse;

public class BookAdapter {

	public GetBookResponse mapBookToResponse(Book book) {
		GetBookResponse response = new GetBookResponse();
		response.setId(book.getId());
		response.setAuthor(book.getAuthor());
		response.setTitle(book.getTitle());
		return response;
	}

	public Book mapRequestToEntity(AddBookRequest book) {
		if (Optional.ofNullable(book.getAuthor()).isPresent() && Optional.ofNullable(book.getTitle()).isPresent()) {
			Book entity = new Book();
			entity.setAuthor(book.getAuthor());
			entity.setTitle(book.getTitle());
			return entity;
		}
		throw new InvalidBookRequestException();
	}

}
