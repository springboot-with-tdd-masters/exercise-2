package com.example.exercise2.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.exercise2.entity.BookEntity;
import com.example.exercise2.service.impl.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class BookControllerTest {

	@Autowired
	private MockMvc mckmvc;

	@Autowired
	private ObjectMapper modelMapper;

	@MockBean
	private BookServiceImpl bookService;

	private BookEntity bookEntity;

	@Test
	@DisplayName("Should return book from db")
	public void shouldReturnBooks_Test() throws Exception {

		when(bookService.findAll()).thenReturn(List.of(new BookEntity("Programming Master Class1", "Mr. Pogi1"),
				new BookEntity("Programming Master Class2", "Mr. Pogi2")));
		mckmvc.perform(get("/book").contentType(MediaType.APPLICATION_JSON)
				.content(modelMapper.writeValueAsString(bookEntity))).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Should save book to db")
	public void shouldSaveBooks_Test() throws Exception {

		bookEntity = new BookEntity("Programming Master Class", "Mr. Pogi");
		when(bookService.saveBook(bookEntity)).thenReturn(bookEntity);
		mckmvc.perform(post("/book").contentType(MediaType.APPLICATION_JSON)
				.content(modelMapper.writeValueAsString(bookEntity))).andExpect(status().isOk());
	}
}
