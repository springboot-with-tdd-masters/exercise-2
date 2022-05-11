package com.example.BookTDD.service;

import com.example.BookTDD.repository.BookRepository;
import com.example.BookTDD.repository.entity.BookEntity;
import com.example.BookTDD.service.adapters.BookEntityToBookAdapter;
import com.example.BookTDD.service.adapters.BookToBookEntityAdapter;
import com.example.BookTDD.service.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class BookServiceTest {


    @Mock
    private BookToBookEntityAdapter bookToBookEntityAdapter;

    @Mock
    private BookEntityToBookAdapter bookEntityToBookAdapter;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService = new BookServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    @DisplayName("Save should accept Book and convert Book to Book entity for saving")
    public void saveShould() {
        //arrange
        Book book = new Book("Panday", "Mars");

        BookEntity convertedBookEntityFromBook = mock(BookEntity.class);

        Mockito.when(bookToBookEntityAdapter.convert(book))
                .thenReturn(convertedBookEntityFromBook);

        BookEntity savedBookFromRepository = mock(BookEntity.class);

        Mockito.when(bookRepository.save(convertedBookEntityFromBook))
                .thenReturn(savedBookFromRepository);

        Book mappedSavedBook = mock(Book.class);
        Mockito.when(bookEntityToBookAdapter.convert(savedBookFromRepository))
                .thenReturn(mappedSavedBook);

        //act
        Book actualSavedBook = bookService.save(book);

        //assert
        verify(bookToBookEntityAdapter)
                .convert(book);
        verify(bookRepository)
                .save(convertedBookEntityFromBook);
        verify(bookEntityToBookAdapter)
                .convert(savedBookFromRepository);
        assertThat(actualSavedBook)
                .isEqualTo(mappedSavedBook);

    }
}
