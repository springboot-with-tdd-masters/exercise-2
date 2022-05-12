package com.exercise2.controller;

import com.exercise2.model.Book;
import com.exercise2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping()
    public ResponseEntity<Book> save(@RequestBody Book bookRequest) {

        bookService.save(bookRequest);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {

        Book book = bookService.findById(id);

        return new ResponseEntity<>(book, HttpStatus.OK);

    }

}
