package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BookControllerTest {

    @Autowired
    private BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void getBookByIdTest() throws Exception {
        //mock the data, mock the service
        Book mockBook = new Book();
        mockBook.setId(1L);
        mockBook.setAuthor("Rey");
        mockBook.setTitle("The TDD");

        when(bookService.getBookById(anyLong()))
                .thenReturn(mockBook);

        //rest call
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/books/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Rey"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The TDD"))
                .andExpect(status().isOk())
        ;
    }



    @Test
    public void addBook() throws Exception {

        Book mockBook = new Book();
        mockBook.setId(1L);
        mockBook.setAuthor("Rey");
        mockBook.setTitle("The TDD");

        when(bookService.addBook(any(Book.class)))
                .thenReturn(mockBook);

        //rest call
        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/books")
                .content(new ObjectMapper().writeValueAsString(mockBook))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Rey"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The TDD"))
        ;
    }
}
