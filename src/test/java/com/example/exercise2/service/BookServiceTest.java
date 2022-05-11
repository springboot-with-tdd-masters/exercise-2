package com.example.exercise2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.exercise2.model.Book;
import com.example.exercise2.repository.BookRepository;

public class BookServiceTest {

	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookService bookService;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
	@DisplayName("Should save Book entity and return correct details")
	public void shouldSaveBookAndReturnDetails() {
		Book book = new Book();
		book.setTitle("Harry Potter and the Sorcerer's Stone");
		book.setAuthor("J.K. Rowling");
		
		Book expectedResponse = new Book();
		expectedResponse.setId(1L);
		expectedResponse.setTitle("Harry Potter and the Sorcerer's Stone");
		expectedResponse.setAuthor("J.K. Rowling");
		
		when(bookRepository.save(book))
			.thenReturn(expectedResponse);
	    
	    Book actualResponse = bookService.addBook(book);
	    
	    verify(bookRepository)
	    	.save(book);
	    assertEquals(expectedResponse, actualResponse);
	}
	
	@Test
	@DisplayName("Should fetch all Books with correct details")
	public void shouldReturnAllBooks() {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("Harry Potter and the Sorcerer's Stone");
		book.setAuthor("J.K. Rowling");

		Book book2 = new Book();
		book2.setId(2L);
		book2.setTitle("Harry Potter and the Chamber of Secrets");
		book2.setAuthor("J.K. Rowling");

		List<Book> books = Arrays.asList(book, book2);
		
		when(bookRepository.findAll())
			.thenReturn(books);
	    
		List<Book> bookList = bookService.getAllBooks();
	    
	    verify(bookRepository)
	    	.findAll();
	    
	    assertThat(bookList)
     		.hasSize(2)
     		.contains(book, book2);
	}
}
