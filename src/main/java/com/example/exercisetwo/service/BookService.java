package com.example.exercisetwo.service;

import java.util.List;

import com.example.exercisetwo.exception.RecordNotFoundException;
import com.example.exercisetwo.model.Book;

public interface BookService {
	
	public Book saveBook(Book book) throws RecordNotFoundException;
	
	public List<Book> getBooks();

}
