package com.example.exercise2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exercise2.entity.BookEntity;
import com.example.exercise2.exception.RecordNotFoundException;
import com.example.exercise2.repository.BookRepository;
import com.example.exercise2.service.impl.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

	@InjectMocks
	private BookServiceImpl bookService;

	@Mock
	private BookRepository bookRepository;

	@Test
	@DisplayName("Should save book to db")
	public void shouldSaveBook_Test() {
		
		// arrange
		BookEntity book = new BookEntity("Programming Master Class", "Mr. Pogi");

		// act
		when(bookRepository.save(book)).thenReturn(book);
		BookEntity savedBook = bookRepository.save(book);

		// assert
		assertThat(savedBook).extracting("title", "author").containsExactly("Programming Master Class", "Mr. Pogi");

	}
	
	@Test
	@DisplayName("Should find all available books")
	public void shouldFindAvailableBooks_Test() {
		
		//arrange
		BookEntity book1 = new BookEntity("Programming Master Class1", "Mr. Pogi1");
		BookEntity book2 = new BookEntity("Programming Master Class2", "Mr. Pogi2");
		List<BookEntity> booksExpected = new ArrayList<>();
		booksExpected.add(book1);
		booksExpected.add(book2);
		when(bookRepository.findAll()).thenReturn(booksExpected);
		
		//act
		List<BookEntity> savedBook = bookService.findAll();
		
		//assert
		assertEquals(booksExpected, savedBook);
	}
	
	@Test
	@DisplayName("Should find all available books")
	public void shouldThrowErrorFindAll_Test() {
		
		List<BookEntity> booksExpected = new ArrayList<>();
		when(bookRepository.findAll()).thenReturn(booksExpected);
		assertThrows(RecordNotFoundException.class, () -> bookService.findAll());
	}

}
