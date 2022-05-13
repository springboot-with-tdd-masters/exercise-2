package com.example.exercisetwo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.exercisetwo.model.Book;
import com.example.exercisetwo.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	BookService service;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	@DisplayName("Save - should save book and return a success response")
	public void saveBookTest() throws JsonProcessingException, Exception {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("Harry Potter");
		book.setAuthor("JK Rowling");

		Book newBook = new Book();
		newBook.setTitle("Harry Potter");
		newBook.setAuthor("JK Rowling");

		Mockito.when(service.saveBook(ArgumentMatchers.any())).thenReturn(book);

		this.mockMvc
				.perform(post("/books").content(mapper.writeValueAsString(newBook))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Harry Potter"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("JK Rowling"));
	}

	@Test
	@DisplayName("Retrieve - should retrieve all books record")
	public void getBooksTest() throws Exception {
		List<Book> books = new ArrayList<>();

		Book book1 = new Book();
		book1.setId(1L);
		book1.setTitle("Harry Potter");
		book1.setAuthor("JK Rowling");

		Book book2 = new Book();
		book2.setId(2L);
		book2.setTitle("Dekada 70");
		book2.setAuthor("Lualhati Bautista");

		books.add(book1);
		books.add(book2);

		Mockito.when(service.getBooks()).thenReturn(books);

		mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Harry Potter"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("JK Rowling"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Dekada 70"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Lualhati Bautista"));
	}

}
