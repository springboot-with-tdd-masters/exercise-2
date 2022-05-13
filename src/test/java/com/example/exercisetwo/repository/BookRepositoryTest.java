package com.example.exercisetwo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.exercisetwo.model.Book;

@DataJpaTest
public class BookRepositoryTest {
	
	@Autowired
	private BookRepository repository;
	
	@Test
	@DisplayName("Save - should accept book entity")
	public void saveBookTest() {
		
		Book book = new Book(1L, "Harry Potter", "JK Rowling");
		Book savedBook = repository.save(book);
		
		assertThat(savedBook).extracting("id", "title", "author").containsExactly(1L, "Harry Potter", "JK Rowling");
		
	}
	
	@Test
	@DisplayName("Retrieve - should retrieve all book records")
	public void getBooks() {
		Book book1 = new Book();
		book1.setTitle("Harry Potter");
		book1.setAuthor("JK Rowling");
		
		Book book2 = new Book();
		book2.setTitle("Dekada 70");
		book2.setAuthor("Lualhati Bautista");
		
		Book book3 = new Book();
		book3.setTitle("Mamba Mentality");
		book3.setAuthor("Kobe Bryant");
		
		Book newBook1 = repository.save(book1);
		Book newBook2 = repository.save(book2);
		Book newBook3 = repository.save(book3);
		
		assertNotNull(repository.findAll());
		assertThat(repository.findAll()).hasSize(3).contains(newBook1, newBook2, newBook3);
	}
}
