package com.exercise2.controller;

import com.exercise2.model.Book;
import com.exercise2.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Save endpoint should return 200 on successful service invocation.")
    public void save() throws Exception {
        //arrange
        Book bookRequest = new Book("Harry Potter", "JK. Rowling");
        //act
        MockHttpServletResponse response = mockMvc.perform(post("/books").content(objectMapper
                .writeValueAsString(bookRequest)).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("Find By ID Endpoint")
    public void findById() throws Exception {

        Book bookRequest = new Book("Harry Potter", "JK. Rowling");

        when(bookService.findById(1L))
                .thenReturn(bookRequest);
        //act
        MockHttpServletResponse response = mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(bookRequest));

    }
}
