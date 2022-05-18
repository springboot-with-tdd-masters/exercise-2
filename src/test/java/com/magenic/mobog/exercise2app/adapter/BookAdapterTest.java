package com.magenic.mobog.exercise2app.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.magenic.mobog.exercise2app.entities.Author;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.magenic.mobog.exercise2app.adapters.BookAdapter;
import com.magenic.mobog.exercise2app.entities.Book;
import com.magenic.mobog.exercise2app.exceptions.InvalidBookRequestException;
import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.responses.GetBookResponse;

class BookAdapterTest {
	
	private BookAdapter bookAdapter;
	
	@BeforeEach
	void setup() {
		bookAdapter = new BookAdapter();
	}

	
	@Test
	@DisplayName("should map BookResponse to BookEntity")
	void shouldMapBookRequestToBookEntity() {
		// given
		AddBookRequest request = new AddBookRequest();
		request.setAuthorId(1L);
		request.setTitle("Catwoman");
		
		// when
		Book actual = bookAdapter.mapRequestToEntity(request);
		
		// then
		assertEquals("Catwoman", actual.getTitle());
	}
	@Test
	@DisplayName("should map BookEntity to BookResponse")
	void shouldMapBookEntityToBookResponse() {
		// given
		Book entity = new Book();
		Author author = new Author();
		author.setId(1L);
		author.setName("Paul Dano");
		entity.setAuthor(author);
		entity.setTitle("There Will Be Riddler");
		entity.setId(1L);
		
		// when
		GetBookResponse actual = bookAdapter.mapBookToResponse(entity);
		
		// then
		assertEquals(1L, actual.getId());
		assertEquals(1L, actual.getAuthorId());
		assertEquals("There Will Be Riddler", actual.getTitle());
	}
	@Test
	@DisplayName("should throw exception if author or title is empty")
	void shouldThrowExceptionOnWhitespaces() {
		// given
		AddBookRequest request = new AddBookRequest();
		assertThrows(InvalidBookRequestException.class, () -> {
			bookAdapter.mapRequestToEntity(request);
		});
	}
}
