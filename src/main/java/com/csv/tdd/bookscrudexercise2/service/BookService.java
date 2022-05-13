package com.csv.tdd.bookscrudexercise2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csv.tdd.bookscrudexercise2.exception.RecordNotFoundException;
import com.csv.tdd.bookscrudexercise2.model.BookEntity;
import com.csv.tdd.bookscrudexercise2.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepository;

	public BookEntity saveBook(BookEntity bookEntity) {
		
		return bookRepository.save(bookEntity);
		
	}
	
	public List<BookEntity> findAll() throws RecordNotFoundException {
		
		List<BookEntity> bookList = bookRepository.findAll();
		
		if(bookList.isEmpty())
			throw new RecordNotFoundException("Book Record does not exist");
		
		return bookList;
	}
	
}
