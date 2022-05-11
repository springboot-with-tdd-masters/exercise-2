package com.example.BookTDD.repository;

import com.example.BookTDD.repository.entity.BookEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {


    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Save should accept Book Entity and save it with the correct details")
    public void saveSunnyDay() {
        //arrange
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle("Panday");
        bookEntity.setAuthor("Mars");

        //act
        BookEntity savedBook = bookRepository.save(bookEntity);

        //assert
        assertThat(savedBook)
                .extracting("title", "author")
                .containsExactly("Panday", "Mars");
    }
}
