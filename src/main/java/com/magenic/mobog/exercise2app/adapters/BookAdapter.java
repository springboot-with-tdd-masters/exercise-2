package com.magenic.mobog.exercise2app.adapters;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.magenic.mobog.exercise2app.entities.Book;
import com.magenic.mobog.exercise2app.exceptions.InvalidBookRequestException;
import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.responses.GetBookResponse;

@Service
public class BookAdapter {

	public GetBookResponse mapBookToResponse(Book book) {
		GetBookResponse response = new GetBookResponse();
		response.setId(book.getId());
		response.setAuthorId(book.getAuthor().getId());
		response.setTitle(book.getTitle());
		return response;
	}

	public Book mapRequestToEntity(AddBookRequest book) {
		if (Optional.ofNullable(book.getAuthorId()).isPresent() && Optional.ofNullable(book.getTitle()).isPresent()) {
			Book entity = new Book();
			entity.setTitle(book.getTitle());
			return entity;
		}
		throw new InvalidBookRequestException();
	}

}
