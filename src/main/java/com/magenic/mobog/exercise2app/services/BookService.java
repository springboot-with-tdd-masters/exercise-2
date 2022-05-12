package com.magenic.mobog.exercise2app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.magenic.mobog.exercise2app.adapters.BookAdapter;
import com.magenic.mobog.exercise2app.exceptions.InvalidBookRequestException;
import com.magenic.mobog.exercise2app.repositories.BookRepository;
import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.responses.GetBookResponse;

@Service
public class BookService {

	private BookAdapter adapter;
	private BookRepository repository;

	public BookService(BookAdapter adapter, BookRepository repository) {
		this.adapter = adapter;
		this.repository = repository;
	}

	GetBookResponse save(AddBookRequest request) {
		return Optional.ofNullable(request).map(adapter::mapRequestToEntity).map(repository::save)
				.map(adapter::mapBookToResponse).orElseThrow(InvalidBookRequestException::new);
	}

	List<GetBookResponse> findAll() {
		return repository.findAll().stream().map(adapter::mapBookToResponse).collect(Collectors.toList());
	}
}
