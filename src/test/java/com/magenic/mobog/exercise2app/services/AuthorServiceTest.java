package com.magenic.mobog.exercise2app.services;

import com.magenic.mobog.exercise2app.adapters.AuthorAdapter;
import com.magenic.mobog.exercise2app.adapters.BookAdapter;
import com.magenic.mobog.exercise2app.entities.Author;
import com.magenic.mobog.exercise2app.entities.Book;
import com.magenic.mobog.exercise2app.exceptions.InvalidRequestException;
import com.magenic.mobog.exercise2app.repositories.AuthorRepository;
import com.magenic.mobog.exercise2app.repositories.BookRepository;
import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.requests.AddAuthorRequest;
import com.magenic.mobog.exercise2app.responses.AuthorResponse;
import com.magenic.mobog.exercise2app.responses.BookResponse;
import com.magenic.mobog.exercise2app.responses.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;

    AuthorAdapter authorAdapter;

    BookAdapter bookAdapter;

    AuthorService service;

    @BeforeEach
    void setup(){
        authorAdapter = new AuthorAdapter();
        bookAdapter = new BookAdapter();
        service = new AuthorService(authorRepository, bookRepository,authorAdapter, bookAdapter);
    }
    @Test
    @DisplayName("should return a correct response for createAuthor")
    void shouldReturnCorrectResponseForCreateAuthor(){
        // given
        AddAuthorRequest request = new AddAuthorRequest();
        request.setName("Johnny Bravo");
        Author saved = new Author();
        saved.setId(1L);
        saved.setName("Johnny Bravo");
        saved.setCreatedDate(LocalDateTime.now());
        saved.setLastModifiedDate(LocalDateTime.now());
        when(authorRepository.save(any(Author.class))).thenReturn(saved);
        // when
        AuthorResponse actual = service.createAuthor(request);
        // then
        assertEquals(1L, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());
        assertEquals("Johnny Bravo",actual.getName());
    }
    @Test
    @DisplayName("should return a correct exception for createAuthor")
    void shouldReturnCorrectExceptionForCreateAuthor(){
        AddAuthorRequest request = new AddAuthorRequest();
        request.setName("Johnny Bravo");
        // when
        assertThrows(InvalidRequestException.class,() -> service.createAuthor(request));
        // then
    }

    @Test
    @DisplayName("should return a correct response for addBookToAuthor")
    void shouldReturnCorrectResponseWhenAddBookIsCalled(){
        // given
        Author dummyAuthor = new Author();
        dummyAuthor.setId(1L);
        dummyAuthor.setName("Jane Doe");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(dummyAuthor));

        Book saved = new Book();
        saved.setAuthor(dummyAuthor);
        saved.setTitle("Divine Tempest");
        saved.setId(1L);
        saved.setCreatedDate(LocalDateTime.now());
        saved.setLastModifiedDate(LocalDateTime.now());
        when(bookRepository.save(any())).thenReturn(saved);
        // when
        AddBookRequest request = new AddBookRequest();
        request.setTitle("Divine Tempest");
        BookResponse actual = service.addBookToAuthor(1L, request);
        // then
        assertEquals("Divine Tempest", actual.getTitle());
        assertEquals(1L, actual.getAuthorId());
        assertEquals(1L, actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());
    }
    @Test
    @DisplayName("should return a correct exception for addBookToAuthor")
    void shouldReturnCorrectException(){
        // given
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        // when
        AddBookRequest request = new AddBookRequest();
        request.setTitle("Divine Tempest");
        // then
        assertThrows(InvalidRequestException.class, () -> service.addBookToAuthor(1L, request));
    }
    @Test
    @DisplayName("should return a page with correct content for findAuthorsWithPage")
    void shouldReturnPageWithCorrectContentForFindAuthors(){
        // given
        Pageable request = PageRequest.of(0, 1);
        Author author1 = new Author();
        author1.setName("Adam");
        author1.setLastModifiedDate(LocalDateTime.now());
        author1.setCreatedDate(LocalDateTime.now());
        Author author2 = new Author();
        author2.setName("Eve");
        author2.setLastModifiedDate(LocalDateTime.now());
        author2.setCreatedDate(LocalDateTime.now());
        Author author3 = new Author();
        author3.setName("Cain");
        author3.setLastModifiedDate(LocalDateTime.now());
        author3.setCreatedDate(LocalDateTime.now());
        Page<Author> page = mock(Page.class);
        when(page.get()).thenReturn(Stream.of(author1));
        when(page.getPageable()).thenReturn(request);
        when(page.getTotalPages()).thenReturn(3);
        // mock
        when(authorRepository.findAll(any(Pageable.class))).thenReturn(page);
        // when
        PageResponse<AuthorResponse> actual = service.findAuthorsWithPage(request);
        // then
        AuthorResponse expected = new AuthorResponse();
        expected.setName(author1.getName());
        expected.setId(author1.getId());
        expected.setCreatedAt(author1.getCreatedDate().toString());
        expected.setUpdatedAt(author1.getLastModifiedDate().toString());
        assertEquals(1, actual.getSize());
        assertEquals(List.of(expected), actual.getContent());
        assertEquals(0, actual.getPage());
        assertEquals(3, actual.getTotalPage());
    }
    @Test
    @DisplayName("should return a page with empty content for findAuthorsWithPage")
    void shouldReturnPageWithEmptyContentForFindAuthors(){
        // given
        Pageable request = PageRequest.of(2, 2);
        Page<Author> page = mock(Page.class);
        when(page.get()).thenReturn(Stream.empty());
        when(page.getPageable()).thenReturn(request);
        when(page.getTotalPages()).thenReturn(3);
        // mock
        when(authorRepository.findAll(any(Pageable.class))).thenReturn(page);
        // when
        PageResponse<AuthorResponse> actual = service.findAuthorsWithPage(request);
        // then
        assertEquals(2, actual.getSize());
        assertEquals(new ArrayList<>(), actual.getContent());
        assertEquals(2, actual.getPage());
        assertEquals(3, actual.getTotalPage());
    }

    @Test
    @DisplayName("should return a page with correct content for findBookByAuthorWithPage")
    void shouldReturnPageWithCorrectContentForFindBookByAuthors(){
        // given
        Pageable request = PageRequest.of(0, 1);
        Author author = new Author();
        author.setId(1L);
        Book book1 = new Book();
        book1.setAuthor(author);
        book1.setTitle("Divine Tempest");
        book1.setCreatedDate(LocalDateTime.now());
        book1.setLastModifiedDate(LocalDateTime.now());
        Page<Book> page = mock(Page.class);
        when(page.get()).thenReturn(Stream.of(book1));
        when(page.getPageable()).thenReturn(request);
        when(page.getTotalPages()).thenReturn(3);
        // mock
        when(bookRepository.findAllByAuthorId(anyLong(), any(Pageable.class))).thenReturn(page);
        // when
        PageResponse<BookResponse> actual = service.findBookByAuthorWithPage(1L, request);
        // then
        BookResponse expected = new BookResponse();
        expected.setTitle(book1.getTitle());
        expected.setId(book1.getId());
        expected.setAuthorId(book1.getAuthor().getId());
        expected.setCreatedAt(book1.getCreatedDate().toString());
        expected.setUpdatedAt(book1.getLastModifiedDate().toString());
        assertEquals(1, actual.getSize());
        assertEquals(List.of(expected), actual.getContent());
        assertEquals(0, actual.getPage());
        assertEquals(3, actual.getTotalPage());
    }
    @Test
    @DisplayName("should return a page with empty content for findBookByAuthorWithPage")
    void shouldReturnPageWithEmptyContentForFindBookByAuthors(){
        // given
        Pageable request = PageRequest.of(2, 2);
        Page<Book> page = mock(Page.class);
        when(page.get()).thenReturn(Stream.empty());
        when(page.getPageable()).thenReturn(request);
        when(page.getTotalPages()).thenReturn(3);
        // mock
        when(bookRepository.findAllByAuthorId(anyLong(), any(Pageable.class))).thenReturn(page);
        // when
        PageResponse<BookResponse> actual = service.findBookByAuthorWithPage(1L, request);
        // then
        assertEquals(2, actual.getSize());
        assertEquals(new ArrayList<>(), actual.getContent());
        assertEquals(2, actual.getPage());
        assertEquals(3, actual.getTotalPage());
    }

}
