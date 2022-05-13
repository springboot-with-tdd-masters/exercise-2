/**
 * 
 */
package com.training.exercise1.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.training.exercise1.model.Book;

/**
 * @author michaeldelacruz
 *
 */

@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	
	@Test
	@DisplayName("Verify injected components")
	public void verifyComponents() {
		assertThat(bookRepository).isNotNull();
	}
	
	@Test
	@DisplayName("Should accept book details and save it")
	public void saveBook() {
		Book book = new Book(1L, "The Christmas Pig", "JK Rowling");
		Book savedBook = bookRepository.save(book);
		
		assertThat(savedBook)
			.extracting("title", "author")
			.containsExactly("The Christmas Pig", "JK Rowling");
	}
	
	@Test
	@DisplayName("Should return retrieved books")
	public void findAllBooks() {
		Book book1 = new Book(1L, "The Christmas Pig", "JK Rowling");
		Book savedBook1 = bookRepository.save(book1);
		
		Book book2 = new Book(1L, "The Overstory", "Richard Powers");
		Book savedBook2 = bookRepository.save(book2);
		
		Book book3 = new Book(1L, "Celestial Bodies", "Jokha Alharthi");
		Book savedBook3 = bookRepository.save(book3);
		
		List<Book> books = bookRepository.findAll();
		
		assertThat(books).hasSizeGreaterThan(0);
		assertThat(books).hasSize(3).contains(savedBook1, savedBook2, savedBook3);
		
	}
}
