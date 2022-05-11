package com.example.BookTDD.controllers;

import com.example.BookTDD.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Save endpoint should return 200 on successful service invocation")
    public void name() throws Exception {
        //arrange

        //act
//        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        //assert

    }
}
