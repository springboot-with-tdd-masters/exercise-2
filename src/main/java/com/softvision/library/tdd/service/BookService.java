package com.softvision.library.tdd.service;

import com.softvision.library.tdd.model.Book;

import java.util.List;

public interface BookService {
    Book createOrUpdate(Book book);

    List<Book> getAll();
}
