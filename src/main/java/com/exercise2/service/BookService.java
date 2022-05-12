package com.exercise2.service;

import com.exercise2.model.Book;

public interface BookService {

    Book save(Book book);

    Book findById(Long i);
}
