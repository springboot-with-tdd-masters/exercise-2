package com.magenic.mobog.exercise2app.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magenic.mobog.exercise2app.exceptions.GlobalExceptionHandler;
import com.magenic.mobog.exercise2app.exceptions.InvalidBookRequestException;
import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.responses.GetBookResponse;
import com.magenic.mobog.exercise2app.services.BookService;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private BookController controller;
	@Autowired 
	private ObjectMapper mapper;
	
	@MockBean
	BookService service;
	
	@BeforeEach
	void setup() {
		controller = new BookController(service);
		mvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new GlobalExceptionHandler()).build();
	}

	@Test
	@DisplayName("should throw 400 for save with empty request body")
	void shouldThrow400BadRequestForEmptyRequestBody() throws Exception {
		// when
		ResultActions actual = mvc.perform(MockMvcRequestBuilders.post("/book").contentType(MediaType.APPLICATION_JSON_VALUE));
		// then
		actual.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	@Test
	@DisplayName("should return saved response with status 200")
	void shouldReturn200OkForCorrectRequestBody() throws Exception {
		// given
		GetBookResponse response = new GetBookResponse();
		response.setId(1L);
		response.setAuthor("Baby Ruth");
		response.setTitle("Baseball Bat");
		when(service.save(any(AddBookRequest.class))).thenReturn(response);
		
		String content = "{\"author\": \"Baby Ruth\", \"title\":\"Baseball Bat\"}";
		// when
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.post("/book")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE));
		// then
		actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.author", is("Baby Ruth")));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Baseball Bat")));
	}
	@Test
	@DisplayName("should return 400 for body with null values")
	void shouldReturn400BadRequestForRequestWithNullValues() throws Exception {
		when(service.save(any())).thenThrow(InvalidBookRequestException.class);
		String content = "{\"author\": null, \"title\":null}";
		// when
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.post("/book")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE));
		actual.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@DisplayName("should return empty list if no books")
	void shouldReturnEmptyListIfServiceReturnsEmptyList() throws Exception {
		when(service.findAll()).thenReturn(new ArrayList<>());
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.get("/book")
				.contentType(MediaType.APPLICATION_JSON_VALUE));
		actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		actual.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.empty()));
	}
	@Test
	@DisplayName("should return list of books if books are found")
	void shouldReturn200WithBodyIfBooksAreFound() throws Exception {
		GetBookResponse response1 = new GetBookResponse();
		response1.setAuthor("Adam");
		response1.setTitle("Apple is Bad");
		response1.setId(1L);
		GetBookResponse response2 = new GetBookResponse();
		response2.setAuthor("Eve");
		response2.setTitle("Just a Bite");
		response2.setId(2L);
		when(service.findAll()).thenReturn(List.of(response1, response2));
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.get("/book")
				.contentType(MediaType.APPLICATION_JSON_VALUE));
		actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		actual.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$[0].author", is("Adam")));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$[1].author", is("Eve")));
	}
}
