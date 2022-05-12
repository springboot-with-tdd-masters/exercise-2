package com.exercise2.repository;

import com.exercise2.model.BookEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {


    @Autowired
    private BookCrudRepository bookCrudRepository;

    @Test
    @DisplayName("Save - should accept Book Entity ")
    public void save() {

        //arrange

        //act
        BookEntity book = new BookEntity("Harry Potter", "J.K. Rowling");
        BookEntity savedBook = bookCrudRepository.save(book);
        //assert

        assertThat(savedBook)
                .extracting("title", "author")
                .containsExactly("Harry Potter", "J.K. Rowling");
    }

    @Test
    @DisplayName("Update - should accept Book Entity")
    public void update() {

        BookEntity book = new BookEntity("Harry Potter", "J.K. Rowling");
        BookEntity savedBook = bookCrudRepository.save(book);
        //assert
        savedBook.setTitle("Updated Harry Potter");
        savedBook.setAuthor("Updated J.K. Rowling");

        bookCrudRepository.save(savedBook);

        assertThat(savedBook)
                .extracting("title", "author")
                .containsExactly("Updated Harry Potter", "Updated J.K. Rowling");
    }

    @Test
    @DisplayName("Find By ID - should accept ID")
    public void findById() {
        BookEntity book = new BookEntity("Harry Potter", "J.K. Rowling");
        BookEntity savedBook = bookCrudRepository.save(book);

        Optional<BookEntity> retrievedBook = bookCrudRepository.findById(savedBook.getId());

        assertThat(retrievedBook).isNotNull();

    }

    @Test
    @DisplayName("Delete By ID - should accept ID")
    public void delete() {
        BookEntity book = new BookEntity("Harry Potter", "J.K. Rowling");
        BookEntity savedBook = bookCrudRepository.save(book);

        Long bookId = savedBook.getId();

        bookCrudRepository.delete(savedBook);

        Optional<BookEntity> retrievedBook = bookCrudRepository.findById(bookId);

        assertThat(retrievedBook).isEmpty();

    }
}
