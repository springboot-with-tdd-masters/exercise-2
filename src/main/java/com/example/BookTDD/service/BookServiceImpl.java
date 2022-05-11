package com.example.BookTDD.service;

import com.example.BookTDD.repository.BookRepository;
import com.example.BookTDD.repository.entity.BookEntity;
import com.example.BookTDD.service.adapters.BookEntityToBookAdapter;
import com.example.BookTDD.service.adapters.BookToBookEntityAdapter;
import com.example.BookTDD.service.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookToBookEntityAdapter bookToBookEntityAdapter;

    @Autowired
    private BookEntityToBookAdapter bookEntityToBookAdapter;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(Book book) {

        //adapter to convert book -> book entity
        BookEntity bookToSave = bookToBookEntityAdapter.convert(book);

        //save the book using book repository
        BookEntity savedBookEntity = bookRepository.save(bookToSave);

        //return the book
        return bookEntityToBookAdapter.convert(savedBookEntity);
    }
}
