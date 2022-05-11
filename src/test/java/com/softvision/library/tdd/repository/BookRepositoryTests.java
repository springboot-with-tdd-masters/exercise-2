package com.softvision.library.tdd.repository;

import com.softvision.library.tdd.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.softvision.library.tdd.LibraryMocks.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTests {

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("Save - should accept different books and save the correct respective details")
    void test_save() {
        String[] properties = {"title", "author"};

        Book savedBook1 = bookRepository.save(getMockBook1());
        assertThat(savedBook1)
                .extracting(properties)
                .containsExactly(MOCK_TITLE_1, MOCK_AUTHOR_1);

        Book savedBook2 = bookRepository.save(getMockBook2());
        assertThat(savedBook2)
                .extracting(properties)
                .containsExactly(MOCK_TITLE_2, MOCK_AUTHOR_2);
    }

    @Test
    @DisplayName("Find All - should be able to retrieve all saved books")
    void test_findAll() {
        bookRepository.save(getMockBook1());
        bookRepository.save(getMockBook2());

        assertThat(bookRepository.findAll())
                .extracting(Book::getAuthor)
                .containsOnly(MOCK_AUTHOR_1, MOCK_AUTHOR_2);
    }

    @AfterEach
    void cleanup() {
        bookRepository.deleteAll();
        assertThat(bookRepository.findAll()).isEmpty();
    }
}
