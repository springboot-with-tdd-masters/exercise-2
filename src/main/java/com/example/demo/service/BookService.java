package com.example.demo.service;

import com.example.demo.model.Book;

public interface BookService {
    public Book getBookById(Long id);
    public Book addBook(Book book);
}
