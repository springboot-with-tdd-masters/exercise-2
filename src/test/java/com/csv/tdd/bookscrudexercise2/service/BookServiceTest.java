package com.csv.tdd.bookscrudexercise2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.csv.tdd.bookscrudexercise2.exception.RecordNotFoundException;
import com.csv.tdd.bookscrudexercise2.model.BookEntity;
import com.csv.tdd.bookscrudexercise2.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@InjectMocks
	private BookService bookService;

	@Mock
	private BookRepository bookRepository;

	@Test
	@DisplayName("Should Save Books to DB")
	public void should_Save_Book() {
		// arrange
		BookEntity bookEntity = new BookEntity("Programming 101", "Mr. Programmer1");
		when(bookRepository.save(any())).thenReturn(bookEntity);

		// act
		BookEntity savedBookEntity = bookService.saveBook(bookEntity);

		// assert
		assertThat(savedBookEntity).extracting("title", "author").containsExactly("Programming 101", "Mr. Programmer1");
	}

	@Test
	@DisplayName("Should Find All Books from DB")
	public void should_Find_All_Books() throws RecordNotFoundException {
		// arrange
		List<BookEntity> bookListExpected = new ArrayList<BookEntity>();
		bookListExpected.add(new BookEntity("Programming 101", "Mr. Programmer 1"));
		bookListExpected.add(new BookEntity("Programming 102", "Mr. Programmer 2"));

		// act
		when(bookRepository.findAll()).thenReturn(bookListExpected);
		List<BookEntity> bookListActual = bookService.findAll();

		// assert
		assertEquals(bookListExpected, bookListActual);
	}

	@Test
	@DisplayName("Should Throw Exception if no book record found in DB")
	public void should_Throw_Exception_In_Find_All_Books() {
		// arrange
		List<BookEntity> bookListExpected = new ArrayList<BookEntity>();

		// act
		when(bookRepository.findAll()).thenReturn(bookListExpected);

		// assert
		assertThrows(RecordNotFoundException.class, () -> bookService.findAll());
	}

}
