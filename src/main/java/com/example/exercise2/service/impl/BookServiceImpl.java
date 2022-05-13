package com.example.exercise2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exercise2.entity.BookEntity;
import com.example.exercise2.exception.RecordNotFoundException;
import com.example.exercise2.repository.BookRepository;
import com.example.exercise2.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public BookEntity saveBook(BookEntity book) {
		
		return bookRepository.save(book);
	}

	@Override
	public List<BookEntity> findAll() throws RecordNotFoundException {
		
		List<BookEntity> books = bookRepository.findAll();
		
		if(books.isEmpty()) {
			throw new RecordNotFoundException("Record Not Found!");
		}
		
		return books;
	}

}
