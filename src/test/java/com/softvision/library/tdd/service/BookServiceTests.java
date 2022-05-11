package com.softvision.library.tdd.service;

import com.softvision.library.tdd.model.Book;
import com.softvision.library.tdd.repository.BookRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static com.softvision.library.tdd.LibraryMocks.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    static final String[] PROPERTIES = {"id", "title", "author"};

    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookService bookService = new BookServiceImpl();

    @Test
    @DisplayName("Create - should create a Book")
    void test_create() {
        final Book book = getMockBook2();
        when(bookRepository.save(book)).thenReturn(book);

        assertThat(bookService.createOrUpdate(book))
                .extracting(PROPERTIES)
                .containsExactly(MOCK_BOOK_ID_2, MOCK_TITLE_2, MOCK_AUTHOR_2);

        verify(bookRepository, atMostOnce()).findById(anyLong());
        verify(bookRepository, atMostOnce()).save(any());
    }

    @Test
    @DisplayName("Update - should update a found Book")
    void test_update() {
        final Book book = getMockBook1();
        book.setAuthor(MOCK_AUTHOR_2);
        book.setTitle(MOCK_TITLE_2);

        when(bookRepository.findById(MOCK_BOOK_ID_1)).thenReturn(Optional.of(getMockBook1()));
        when(bookRepository.save(
                argThat(b -> b.getId() == MOCK_BOOK_ID_1
                        && b.getAuthor().equals(MOCK_AUTHOR_2)
                        && b.getTitle().equals(MOCK_TITLE_2))))
                .thenReturn(book);

        Book updatedBook = bookService.createOrUpdate(book);
        assertThat(updatedBook)
                .extracting(PROPERTIES)
                .containsExactly(MOCK_BOOK_ID_1, MOCK_TITLE_2, MOCK_AUTHOR_2);

        verify(bookRepository, atMostOnce()).findById(anyLong());
        verify(bookRepository, atMostOnce()).save(any());
    }

    @Test
    @DisplayName("Find All - should get a list of books")
    void test_findAll() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(getMockBook1(), getMockBook2()));

        assertThat(bookService.getAll())
                .extracting(Book::getTitle)
                .containsExactly(MOCK_TITLE_1, MOCK_TITLE_2);

        verify(bookRepository, atMostOnce()).findAll();
    }

}
