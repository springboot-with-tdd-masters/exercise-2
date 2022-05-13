package com.csv.tdd.bookscrudexercise2.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.csv.tdd.bookscrudexercise2.model.BookEntity;

@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	
	@Test
	@DisplayName("Should Save Books to DB")
	public void should_Save_Book() {
		//arrange
		BookEntity bookEntity = new BookEntity("Programming 101", "Mr. Programmer");
		
		//act
        BookEntity savedBookEntity = bookRepository.save(bookEntity);

        //assert
		assertThat(savedBookEntity).extracting("title", "author").containsExactly("Programming 101", "Mr. Programmer");
	}
	
	@Test
	@DisplayName("Should Find All Books from DB")
	public void should_Find_All_Books() {
		//arrange
		List<BookEntity> bookListExpected = new ArrayList<BookEntity>();
		bookListExpected.add(new BookEntity("Programming 101", "Mr. Programmer 1"));
		bookListExpected.add(new BookEntity("Programming 102", "Mr. Programmer 2"));
		
		bookRepository.saveAll(bookListExpected);
		
		//act
		List<BookEntity> bookListActual = bookRepository.findAll();
		
		//assert
		assertEquals(bookListExpected, bookListActual);
	}
}
