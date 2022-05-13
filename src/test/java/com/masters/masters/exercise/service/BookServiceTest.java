package com.masters.masters.exercise.service;

import com.masters.masters.exercise.model.BookDtoRequest;
import com.masters.masters.exercise.model.BookDtoResponse;
import com.masters.masters.exercise.model.BookEntity;
import com.masters.masters.exercise.repository.BookRepository;
import com.masters.masters.exercise.services.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
public class BookServiceTest {

    @Mock
    BookRepository repo;

    @InjectMocks
    BookServiceImpl bookService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveHappyPath(){
        BookDtoRequest bookDto = new BookDtoRequest();
        bookDto.setAuthor("author1");
        bookDto.setTitle("title1");
        BookEntity repoResponse = new BookEntity();
        repoResponse.setAuthor("author1");
        repoResponse.setTitle("title1");
        when(repo.save(Mockito.any(BookEntity.class))).thenReturn(repoResponse);
        BookDtoResponse bookDtoResponse = bookService.save(bookDto);
        verify(repo).save(Mockito.any(BookEntity.class));
        assertThat(bookDtoResponse).extracting("title","author").containsExactly("title1","author1");
    }

    @Test
    public void findAllHappyPath(){
        BookEntity book1 = new BookEntity();
        book1.setId(1L);
        book1.setAuthor("author1");
        book1.setTitle("title1");
        BookEntity book2 = new BookEntity();
        book1.setId(2L);
        book2.setAuthor("author2");
        book2.setTitle("title2");
        when(repo.findAll()).thenReturn(List.of(book1,book2));
        List<BookEntity> bookList = bookService.findAllBooks();
        verify(repo).findAll();
        assertThat(bookList).hasSize(2);
    }

    @Test
    public void findAllNoRecord(){
        when(repo.findAll()).thenReturn(List.of());
        List<BookEntity> bookList = bookService.findAllBooks();
        verify(repo).findAll();
        assertThat(bookList).isEmpty();
    }
}
