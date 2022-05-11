package com.example.demo.service;

import com.example.demo.model.Book;

import java.util.Optional;

public interface BookService {
    void addBook(Book book);
    Optional<Book> getBook(Long id);
}
