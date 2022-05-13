/**
 * 
 */
package com.training.exercise1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.exercise1.model.Book;
import com.training.exercise1.service.BookService;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * @author michaeldelacruz
 *
 */

@WebMvcTest
public class BookControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	private BookController bookController;
	
	@MockBean
	private BookService bookService;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	@DisplayName("Should add book through controller")
	public void testAddBook() throws JsonProcessingException, Exception {
		
        Book book = new Book(1L, "The Overstory", "The Richard Powers");
        Book bookResponse = new Book(1L, "The Overstory", "The Richard Powers");
        
        when(bookService.saveOrUpdate(book)).thenReturn(bookResponse);
   
        MvcResult result = mockMvc.perform(post("/books")
                .content(mapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
  
	}
	
	@Test
	@DisplayName("Should test all books through controller")
	public void testFindAllBppl() throws JsonProcessingException, Exception {
		
		List<Book> books = new ArrayList<Book>();
		Book book1 = new Book(1L, "Whereabouts", "Jhumpa Lahiri");
		Book book2 = new Book(2L, "The Bench", "Meghan Markle");
		Book book3 = new Book(3L, "Will", "Will Smith and Mark Manson");

		books.add(book1);
		books.add(book2);
		books.add(book3);

		when(bookService.findAll()).thenReturn(books);
		
		mockMvc.perform(MockMvcRequestBuilders
                .get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
	}
}
