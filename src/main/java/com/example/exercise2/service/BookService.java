package com.example.exercise2.service;

import java.util.List;

import com.example.exercise2.entity.BookEntity;
import com.example.exercise2.exception.RecordNotFoundException;

public interface BookService {

	BookEntity saveBook(BookEntity book);

	List<BookEntity> findAll() throws RecordNotFoundException ;

}
