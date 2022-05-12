package com.exercise2.service;

import com.exercise2.adaptors.BookEntityToBookAdaptor;
import com.exercise2.adaptors.BookToBookEntityAdaptor;
import com.exercise2.model.Book;
import com.exercise2.model.BookEntity;
import com.exercise2.repository.BookCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BookServiceImpl implements BookService{

    @Autowired
    private BookToBookEntityAdaptor bookToBookEntityAdaptor;

    @Autowired
    private BookCrudRepository bookCrudRepository;

    @Autowired
    private BookEntityToBookAdaptor bookEntityToBookAdaptor;

    @Override
    public Book save(Book book) {

        //create mapper or adaptor
        BookEntity bookEntity = bookToBookEntityAdaptor.convert(book);
        //bookService.save
        BookEntity savedBookEntity = bookCrudRepository.save(bookEntity);
        System.out.println("Created User with ID: " + savedBookEntity.getId());

        //return saved entity converted book entity to book domain
        return bookEntityToBookAdaptor.convert(savedBookEntity);
    }

    @Override
    public Book findById(Long i) {
        Optional<BookEntity> retrivedBookEntity = bookCrudRepository.findById(i);
        return bookEntityToBookAdaptor.convert(retrivedBookEntity.get());
    }
}
