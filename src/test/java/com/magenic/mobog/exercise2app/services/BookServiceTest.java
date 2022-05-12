package com.magenic.mobog.exercise2app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.magenic.mobog.exercise2app.adapters.BookAdapter;
import com.magenic.mobog.exercise2app.entities.Book;
import com.magenic.mobog.exercise2app.exceptions.InvalidBookRequestException;
import com.magenic.mobog.exercise2app.repositories.BookRepository;
import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.responses.GetBookResponse;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
	private BookService service;
	
	@Mock
	private BookAdapter adapter;
	
	@Mock
	private BookRepository repository;
	
	@BeforeEach
	void setup() {
		service = new BookService(adapter, repository);
	}
	
	@Test
	@DisplayName("should return saved entity as response")
	void shouldReturnSavedEntityAsResponse() {
		// given
		AddBookRequest request = new AddBookRequest();
		request.setAuthor("Jen Kins");
		request.setTitle("Blue Ocean");
		
		Book mapped = new Book();
		mapped.setAuthor("Jen Kins");
		mapped.setTitle("Blue Ocean");
		
		Book saved = new Book();
		saved.setAuthor(mapped.getAuthor());
		saved.setTitle(mapped.getTitle());
		saved.setId(1L);
		
		GetBookResponse response = new GetBookResponse();
		response.setId(saved.getId());
		response.setAuthor(saved.getAuthor());
		response.setTitle(saved.getTitle());
		
		when(adapter.mapRequestToEntity(request)).thenReturn(mapped);
		when(repository.save(mapped)).thenReturn(saved);
		when(adapter.mapBookToResponse(saved)).thenReturn(response);
		// when
		GetBookResponse actual = service.save(request);
		
		// then
		verify(adapter, times(1)).mapRequestToEntity(request);
		verify(repository, times(1)).save(mapped);
		verify(adapter, times(1)).mapBookToResponse(saved);
		assertEquals(response.getId(), actual.getId());
		assertEquals(response.getAuthor(), actual.getAuthor());
		assertEquals(response.getTitle(), actual.getTitle());
	}
	
	@Test
	@DisplayName("should return list of books")
	void shouldReturnListOfBooks() {
		// given
		Book book1 = new Book();
		book1.setId(1L);
		book1.setTitle("A");
		book1.setAuthor("A A");
		Book book2 = new Book();
		book2.setId(2L);
		book2.setTitle("B");
		book2.setAuthor("B B");
		
		GetBookResponse mapped1 = new GetBookResponse();
		mapped1.setId(1L);
		mapped1.setTitle("A");
		mapped1.setAuthor("A A");
		GetBookResponse mapped2 = new GetBookResponse();
		mapped2.setId(2L);
		mapped2.setTitle("B");
		mapped2.setAuthor("B B");
		List<Book> mockEntities = List.of(book1, book2);
		when(repository.findAll()).thenReturn(mockEntities);
		when(adapter.mapBookToResponse(book1)).thenReturn(mapped1);
		when(adapter.mapBookToResponse(book2)).thenReturn(mapped2);
		// when
		List<GetBookResponse> response = service.findAll();
		
		// then
		verify(repository, times(1)).findAll();
		verify(adapter, times(2)).mapBookToResponse(any());
		
	}
	@Test
	@DisplayName("should throw exception on invalid request")
	void shouldThrowInvalidBookException() {
		AddBookRequest request = new AddBookRequest();
		assertThrows(InvalidBookRequestException.class, () -> service.save(request));
	}
}
