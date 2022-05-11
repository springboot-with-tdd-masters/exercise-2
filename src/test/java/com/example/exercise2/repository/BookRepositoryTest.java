package com.example.exercise2.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.exercise2.model.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

	 @Autowired
	 private BookRepository bookRepository;

	 @Test
	 @DisplayName("Should save Book Entity with the correct details")
	 public void saveBook() {
	     Book book = new Book();
	     book.setTitle("Harry Potter and the Sorcerer's Stone");
	     book.setAuthor("J.K. Rowling");

	     Book savedBook = bookRepository.save(book);

	     assertThat(savedBook)
	     	.extracting("title", "author")
	     	.containsExactly("Harry Potter and the Sorcerer's Stone", "J.K. Rowling");
	 }
	 
	 @Test
	 @DisplayName("Should return all saved Book Entity with the correct details")
	 public void getAllSavedBooks() {
	     Book book = new Book();
	     book.setTitle("Harry Potter and the Sorcerer's Stone");
	     book.setAuthor("J.K. Rowling");

	     Book book2 = new Book();
	     book2.setTitle("Harry Potter and the Chamber of Secrets");
	     book2.setAuthor("J.K. Rowling");
	    
	     Book savedBook1 = bookRepository.save(book);
	     Book savedBook2 = bookRepository.save(book2);
	     
	     assertThat(bookRepository.findAll())
	     	.hasSize(2)
	     	.contains(savedBook1, savedBook2);
	 }
}
