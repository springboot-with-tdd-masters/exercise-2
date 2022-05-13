package com.example.exercise2.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.exercise2.entity.BookEntity;

@DataJpaTest
public class BookRepositoryTest {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	@DisplayName("Should save book to db")
	public void shouldSaveBook_Test() {
		
		//arrange
		BookEntity book = new BookEntity("Programming Master Class", "Mr. Pogi");
		
		//act
		BookEntity savedBook = bookRepository.save(book);
		
		//assert
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
		bookRepository.saveAll(booksExpected);
		
		//act
		List<BookEntity> savedBook = bookRepository.findAll();
		
		//assert
		assertEquals(booksExpected, savedBook);
	}

}
