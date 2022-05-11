package com.example.BookTDD.controller;

import com.example.BookTDD.service.BookService;
import com.example.BookTDD.service.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {


    @Autowired
    private BookService bookService;

    @PostMapping()
    public ResponseEntity save(@RequestBody Book bookRequest) {

        bookService.save(bookRequest);

        return ResponseEntity.ok().build();

    }

    @ExceptionHandler
    public ResponseEntity handleException(RuntimeException e) {
        return ResponseEntity.internalServerError().build();
    }

}
