package com.softvision.library.tdd.controller;

import com.softvision.library.tdd.model.Book;
import com.softvision.library.tdd.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> result = bookService.getAll();
        if (result == null || result.isEmpty()) {
            return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book result = bookService.createOrUpdate(book);
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.CREATED);
    }
}
