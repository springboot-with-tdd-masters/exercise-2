package com.example.exercisetwo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exercisetwo.exception.RecordNotFoundException;
import com.example.exercisetwo.model.Book;
import com.example.exercisetwo.repository.BookRepository;
import com.example.exercisetwo.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepository repository;

	@Override
	public Book saveBook(Book bookEntity) throws RecordNotFoundException {
		Optional<Book> book = repository.findById(bookEntity.getId());
		
		if (book.isPresent()) {
			Book newBook = book.get();
			newBook.setTitle(bookEntity.getTitle());
			newBook.setAuthor(bookEntity.getAuthor());
			
			return repository.save(newBook);
		} else {
			return repository.save(bookEntity);
		}
	}

	@Override
	public List<Book> getBooks() {
		return repository.findAll();
	}

}
