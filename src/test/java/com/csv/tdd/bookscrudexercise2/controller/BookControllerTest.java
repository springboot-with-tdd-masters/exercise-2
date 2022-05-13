package com.csv.tdd.bookscrudexercise2.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.csv.tdd.bookscrudexercise2.model.BookEntity;
import com.csv.tdd.bookscrudexercise2.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	BookService bookService;

	ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("Should Find All Books from DB")
	public void should_Find_All_Books() throws Exception {

		when(bookService.findAll()).thenReturn(List.of(new BookEntity("Programming 101", "Mr. Programmer 1"),
				new BookEntity("Programming 102", "Mr. Programmer 2")));

		this.mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}
	
	@Test
	@DisplayName("Should Save Book to DB")
	public void should_Save_Book() throws Exception {

		when(bookService.saveBook(any())).thenReturn(new BookEntity("Programming 102", "Mr. Programmer 2"));

		this.mockMvc.perform(post("/books").content(objectMapper.writeValueAsString(new BookEntity("Programming 102", "Mr. Programmer 2")))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}
	
}
