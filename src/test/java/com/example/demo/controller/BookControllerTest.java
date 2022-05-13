package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("Fetch all books, response should give http status 200")
    public void successGetAllBooks() throws Exception {
        this.mockMvc.perform(get("/api/getAllBooks"))
                .andExpect(status().isOk());
        verify(bookRepository).findAll();
    }

    @Test
    @DisplayName("Given a successful save, response should give http status 200")
    public void sucessAddNewBook() throws Exception {
        Book bookRequest = Book.builder().author("newAuthor").title("newTitle").build();
        this.mockMvc.perform(post("/api/addBook").content(
                        objectMapper.writeValueAsString(bookRequest)
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookRepository, times(3)).save(any());
    }

    @Test
    @DisplayName("Given a successful update, response should give http status 200")
    public void successUpdateExistingBook() throws Exception {
        Book bookRequest = Book.builder().id(1L).author("updateAuthor").title("updateTitle").build();
        this.mockMvc.perform(put("/api/updateBook").content(
                        objectMapper.writeValueAsString(bookRequest)
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookRepository).findById(any());
    }
}