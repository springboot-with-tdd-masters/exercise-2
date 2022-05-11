package com.example.BookTDD.controllers;

import com.example.BookTDD.service.BookService;
import com.example.BookTDD.service.models.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureMockMvc
public class BookServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Given a successful save, response should give http status 200")
    public void success() throws Exception {


        //act
        Book bookRequest = new Book("Panday", "Mars");
        this.mockMvc.perform(post("/books").content(
                        objectMapper.writeValueAsString(bookRequest)
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService)
                .save(bookRequest);

    }

    @Test
    @DisplayName("Given an error unknown error in save, response should give http status 200")
    public void unknownError() throws Exception {

        when(bookService.save(any()))
                .thenThrow(new RuntimeException());

        //act
        Book bookRequest = new Book("Panday", "Mars");
        this.mockMvc.perform(post("/books").content(
                        objectMapper.writeValueAsString(bookRequest)
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());

        verify(bookService)
                .save(bookRequest);

    }
}
