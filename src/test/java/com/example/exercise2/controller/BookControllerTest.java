package com.example.exercise2.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.exercise2.model.Book;
import com.example.exercise2.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
	@Test
	@DisplayName("Given a successful save, response should give http status 200")
	public void addBookShouldSucceed() throws Exception {
		Book expectedResponse = new Book();
		expectedResponse.setId(1L);
		expectedResponse.setTitle("Harry Potter and the Sorcerer's Stone");
		expectedResponse.setAuthor("J.K. Rowling");

		when(bookService.addBook(any()))
         	.thenReturn(expectedResponse);
		
		Book bookRequest = new Book();
		bookRequest.setTitle("Harry Potter and the Sorcerer's Stone");
		bookRequest.setAuthor("J.K. Rowling");

		this.mockMvc.perform(post("/books/add").content(
	            	objectMapper.writeValueAsString(bookRequest)
	    		).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Harry Potter and the Sorcerer's Stone"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("J.K. Rowling"));

	    verify(bookService).addBook(any());
	}
	
	@Test
	@DisplayName("Fetch all books, response should give http status 200")
	public void shouldReturnSavedBooks() throws Exception {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("Harry Potter and the Sorcerer's Stone");
		book.setAuthor("J.K. Rowling");

		Book book2 = new Book();
		book2.setId(2L);
		book2.setTitle("Harry Potter and the Chamber of Secrets");
		book2.setAuthor("J.K. Rowling");

		List<Book> books = Arrays.asList(book, book2);
		
		when(bookService.getAllBooks())
			.thenReturn(books);
		
		this.mockMvc.perform(get("/books"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Harry Potter and the Sorcerer's Stone"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("J.K. Rowling"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Harry Potter and the Chamber of Secrets"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("J.K. Rowling"));


	    verify(bookService).getAllBooks();
	}
	
	@Test
    @DisplayName("Given an error unknown error in save, response should give http status 500")
    public void unknownError() throws Exception {
        when(bookService.addBook(any()))
                .thenThrow(new RuntimeException());

    	Book bookRequest = new Book();
		bookRequest.setTitle("Harry Potter and the Sorcerer's Stone");
		bookRequest.setAuthor("J.K. Rowling");

		this.mockMvc.perform(post("/books/add").content(
	            	objectMapper.writeValueAsString(bookRequest)
	    		).contentType(MediaType.APPLICATION_JSON))
        	.andExpect(status().is5xxServerError());

        verify(bookService).addBook(any());
	}
}
