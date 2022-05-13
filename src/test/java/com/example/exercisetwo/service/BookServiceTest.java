package com.example.exercisetwo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exercisetwo.exception.RecordNotFoundException;
import com.example.exercisetwo.model.Book;
import com.example.exercisetwo.repository.BookRepository;
import com.example.exercisetwo.service.impl.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
	@InjectMocks
	BookService service = new BookServiceImpl();
	
	@Mock
	BookRepository repository;
	
	@Test
	@DisplayName("Save - should create book record")
	public void saveBookTest() throws RecordNotFoundException {
		
		Book book = new Book(1L, "Harry Potter", "JK Rowling");
		Book newBook = new Book(1L, "Harry Potter", "JK Rowling");
		
		Mockito.when(repository.save(book)).thenReturn(newBook);
		
		Book savedBook = service.saveBook(book);
		
		verify(repository).save(book);
		assertEquals(newBook, savedBook);
				
	}
	
	@Test
	@DisplayName("Retrieve - get all book records")
	public void getBooksTest() {
		
		Book book1 = new Book(1L, "Harry Potter", "JK Rowling");
		Book book2 = new Book(2L, "Dekada 70", "Lualhati Bautista");
		
		List<Book> books = new ArrayList<>();
		books.add(book1);
		books.add(book2);
		
		Mockito.when(repository.findAll()).thenReturn(books);
		
		assertNotNull(service.getBooks());
		assertThat(service.getBooks()).extracting(Book::getId).containsExactly(1L, 2L);
	}

}
