package com.example.demo.repository;

import com.example.demo.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Save a book and return saved book")
    @Order(1)
    @Rollback(value = false)
    public void saveBook() {
        //arrange
        Book book = new Book(1L,"Rey","The TDD");
        //act
        Book newBook = bookRepository.save(book);
        //assert
        assertThat(newBook)
                .extracting("id","author","title")
                .containsExactly(1L,"Rey","The TDD");
    }

    @Test
    @Order(2)
    public void getBook(){

        Book book = bookRepository.findById(1L).get();

        assertThat(book.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getBooks(){

        List<Book> books = bookRepository.findAll();

        assertThat(books.size()).isGreaterThan(0);
    }
}
