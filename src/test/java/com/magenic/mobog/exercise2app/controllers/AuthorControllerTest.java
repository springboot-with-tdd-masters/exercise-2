package com.magenic.mobog.exercise2app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magenic.mobog.exercise2app.exceptions.GlobalExceptionHandler;
import com.magenic.mobog.exercise2app.exceptions.InvalidRequestException;
import com.magenic.mobog.exercise2app.requests.AddAuthorRequest;
import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.responses.AuthorResponse;
import com.magenic.mobog.exercise2app.responses.BookResponse;
import com.magenic.mobog.exercise2app.responses.PageResponse;
import com.magenic.mobog.exercise2app.services.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AuthorController.class)
public class AuthorControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private AuthorController controller;
	@Autowired 
	private ObjectMapper mapper;
	
	@MockBean
	AuthorService service;
	
	@BeforeEach
	void setup() {
		controller = new AuthorController(service);
		mvc = MockMvcBuilders.standaloneSetup(controller)
				.setCustomArgumentResolvers( new PageableHandlerMethodArgumentResolver())
				.setControllerAdvice(new GlobalExceptionHandler()).build();
	}

	@Test
	@DisplayName("should throw 400 for save with empty request body")
	void shouldThrow400BadRequestForEmptyRequestBody() throws Exception {
		// when
		ResultActions actual = mvc.perform(MockMvcRequestBuilders.post("/authors/1/books").contentType(MediaType.APPLICATION_JSON_VALUE));
		// then
		actual.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	@Test
	@DisplayName("should return saved book with status 200")
	void shouldReturn200OkForCorrectRequestBodyWhenCreateBook() throws Exception {
		// given
		BookResponse response = new BookResponse();
		response.setId(1L);
		response.setAuthorId(1L);
		response.setTitle("Baseball Bat");
		when(service.addBookToAuthor(anyLong(), any(AddBookRequest.class))).thenReturn(response);
		
		String content = "{\"title\":\"Baseball Bat\"}";
		// when
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.post("/authors/1/books")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE));
		// then
		actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.authorId", is(1)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.title", is("Baseball Bat")));
	}
	@Test
	@DisplayName("should return saved author with status 200")
	void shouldReturn200OkForCorrectRequestBodyWhenCreateAuthor() throws Exception {
		// given
		AuthorResponse response = new AuthorResponse();
		response.setName("Jane Dee");
		response.setId(1L);
		when(service.createAuthor(any(AddAuthorRequest.class))).thenReturn(response);

		String content = "{\"name\":\"Jane Dee\"}";
		// when
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.post("/authors")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON_VALUE));
		// then
		actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Jane Dee")));
	}
	@Test
	@DisplayName("should return 400 for body with null values")
	void shouldReturn400BadRequestForRequestWithNullValues() throws Exception {
		when(service.addBookToAuthor(anyLong(),any())).thenThrow(InvalidRequestException.class);
		String content = "{\"author\": null, \"title\":null}";
		// when
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.post("/authors/1/books")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE));
		actual.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	@Test
	@DisplayName("should retrieve empty content")
	void shouldRetrieveEmptyContentPageable() throws Exception {
		// given
		PageResponse<AuthorResponse> response = PageResponse
				.<AuthorResponse>builder()
				.size(2)
				.page(0)
				.content(new ArrayList<>())
				.build();
		when(service.findAuthorsWithPage(any())).thenReturn(response);

		// when
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.get("/authors?page=0&size=2")
						.accept(MediaType.APPLICATION_JSON_VALUE));
		// then
		actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.size", is(2)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.page", is(0)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.content", empty()));
	}
	@Test
	@DisplayName("should retrieve empty content when no pageable params passed")
	void shouldRetrieveEmptyContentOnNoPageable() throws Exception {
		// given
		PageResponse<AuthorResponse> response = PageResponse
				.<AuthorResponse>builder()
				.size(2)
				.page(0)
				.content(new ArrayList<>())
				.build();
		when(service.findAuthorsWithPage(any())).thenReturn(response);

		// when
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.get("/authors")
						.accept(MediaType.APPLICATION_JSON_VALUE));
		// then
		actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.size", is(2)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.page", is(0)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.content", empty()));
	}
	@Test
	@DisplayName("should retrieve empty content on find books")
	void shouldRetrieveEmptyBooksPageable() throws Exception {
		// given
		PageResponse<BookResponse> response = PageResponse
				.<BookResponse>builder()
				.size(2)
				.page(0)
				.content(new ArrayList<>())
				.build();
		when(service.findBookByAuthorWithPage(anyLong(), any())).thenReturn(response);

		// when
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.get("/authors/1/books?page=0&size=2")
						.accept(MediaType.APPLICATION_JSON_VALUE));
		// then
		actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.size", is(2)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.page", is(0)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.content", empty()));
	}
	@Test
	@DisplayName("should retrieve empty content on find books with no passed page params")
	void shouldRetrieveEmptyBooksPageableNoPageParams() throws Exception {
		// given
		PageResponse<BookResponse> response = PageResponse
				.<BookResponse>builder()
				.size(2)
				.page(0)
				.content(new ArrayList<>())
				.build();
		when(service.findBookByAuthorWithPage(anyLong(), any())).thenReturn(response);

		// when
		ResultActions actual = mvc.perform(
				MockMvcRequestBuilders.get("/authors/1/books")
						.accept(MediaType.APPLICATION_JSON_VALUE));
		// then
		actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.size", is(2)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.page", is(0)));
		actual.andExpect(MockMvcResultMatchers.jsonPath("$.content", empty()));
	}
}
