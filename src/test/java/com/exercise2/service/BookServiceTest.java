package com.exercise2.service;

import com.exercise2.adaptors.BookEntityToBookAdaptor;
import com.exercise2.adaptors.BookToBookEntityAdaptor;
import com.exercise2.model.Book;
import com.exercise2.model.BookEntity;
import com.exercise2.repository.BookCrudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService = new BookServiceImpl();

    @Mock
    private BookToBookEntityAdaptor bookToBookEntityAdaptor;

    @Mock
    private BookEntityToBookAdaptor bookEntityToBookAdaptor;

    @Mock
    private BookCrudRepository bookCrudRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save ")
    public void savePositiveCase() {

        Book book = new Book("Harry Potter", "J.K. Rowling");

        BookEntity mockedBookEntityFromBookDomain = mock(BookEntity.class);

        BookEntity savedBookEntityfromBookRepository = mock(BookEntity.class);

        when(bookToBookEntityAdaptor.convert(book))
                .thenReturn(mockedBookEntityFromBookDomain);

        when(bookCrudRepository.save(mockedBookEntityFromBookDomain))
                .thenReturn(savedBookEntityfromBookRepository);

        Book convertedBookFromSavedBookEntity = mock(Book.class);
        when(bookEntityToBookAdaptor.convert(savedBookEntityfromBookRepository))
                .thenReturn(convertedBookFromSavedBookEntity);

        Book savedBookDomain = bookService.save(book);

        verify(bookToBookEntityAdaptor)
                .convert(book);

        verify(bookCrudRepository)
                .save(mockedBookEntityFromBookDomain);

        verify(bookEntityToBookAdaptor)
                .convert(savedBookEntityfromBookRepository);

        assertThat(savedBookDomain).isEqualTo(convertedBookFromSavedBookEntity);
    }

    @Test
    @DisplayName("Find By ID: ")
    public void findById() {

        BookEntity bookEntity = new BookEntity("Harry Potter", "Jk Rowling");

        when(bookCrudRepository.findById(1L))
                .thenReturn(Optional.of(bookEntity));

        Book convertedBookFromSavedBookEntity = mock(Book.class);

        bookService.findById(1L);

        when(bookEntityToBookAdaptor.convert(bookEntity))
                .thenReturn(convertedBookFromSavedBookEntity);


        verify(bookCrudRepository)
                .findById(1L);
        verify(bookEntityToBookAdaptor)
                .convert(bookEntity);
    }

}