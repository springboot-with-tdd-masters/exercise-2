package com.magenic.mobog.exercise2app.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.magenic.mobog.exercise2app.entities.Book;

@DataJpaTest
public class BookRepositoryTest {
	
	@Autowired
	TestEntityManager testEntityMgr;
	
	@Autowired
	BookRepository bookRepository;

	@AfterEach
	void teardown() {
		testEntityMgr.clear();
		testEntityMgr.flush();
	}
	
	@Test
	@DisplayName("findAll should retrieve all book")
	void findAllShouldRetrieveAllBooks() {
		// given 
		Book book1 = new Book();
		Book book2 = new Book();
		// when
		testEntityMgr.persist(book1);
		testEntityMgr.persist(book2);
		List<Book> actual = bookRepository.findAll();
		
		// then
		assertNotNull(actual);
		assertEquals(List.of(book1, book2), actual);
		
	}
	@Test
	@DisplayName("save should save book entity")
	void saveShouldSaveBookEntity() {
		// given 
		Book newBook = new Book();
		newBook.setAuthor("Robert Pattinson");
		newBook.setTitle("Twilight: Redux");
		// when
		Book expected = bookRepository.save(newBook);
		Book actual = testEntityMgr.find(Book.class, expected.getId());
		// then
		assertNotNull(actual);
		assertEquals(actual.getAuthor(), expected.getAuthor());
		assertEquals(actual.getTitle(), expected.getTitle());
	}
	
	@Test
	@DisplayName("should return emptyList when repository is empty")
	void shouldReturnEmptyList() {
		// given
		// when
		List<Book> actual = bookRepository.findAll();
		
		// then
		assertEquals(0, actual.size());
	}
}
