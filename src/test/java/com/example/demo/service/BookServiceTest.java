package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService = new BookServiceImpl();


    @Test
    @DisplayName("add Book")
    public void positiveSaveBook() {
        //prepare
        Book book = new Book(1L,"Rey","The TDD");
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        //act
        bookService.addBook(book);
        //assert
        verify(bookRepository)
                .save(book);
        assertThat(book)
                .extracting("id","author","title")
                .containsExactly(1L,"Rey","The TDD");
    }

    @Test
    @DisplayName("get Book")
    public void positiveGetBook() {
        //prepare
        Book book = new Book(1L,"Rey","The TDD");
        Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(Mockito.anyLong())).thenReturn(optionalBook);
        //act
        bookService.getBook(2L);
        //assert
        verify(bookRepository)
                .findById(2L);
        assertThat(book)
                .extracting("id","author","title")
                .containsExactly(1L,"Rey","The TDD");
    }
}
