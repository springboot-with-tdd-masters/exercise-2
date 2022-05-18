package com.magenic.mobog.exercise2app.repositories;

import com.magenic.mobog.exercise2app.entities.Author;
import com.magenic.mobog.exercise2app.entities.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    TestEntityManager testEntityMgr;

    @Autowired
    AuthorRepository authorRepository;

    @AfterEach
    void teardown(){
        testEntityMgr.clear();
        testEntityMgr.flush();
    }
    @Test
    @DisplayName("findAll should retrieve all author")
    void findAllShouldRetrieveAllAuthors() {
        // given
        Author author1 = new Author();
        Author author2 = new Author();
        // when
        testEntityMgr.persist(author1);
        testEntityMgr.persist(author2);
        List<Author> actual = authorRepository.findAll();

        // then
        assertNotNull(actual);
        assertEquals(List.of(author1, author2), actual);

    }
    @Test
    @DisplayName("findAll should retrieve all author with books")
    void findAllShouldRetrieveAuthorWithBooks() {
        // given
        Author author1 = new Author();
        author1.setName("Finn Jake");


        Book book1 = new Book();
        book1.setAuthor(author1);
        book1.setTitle("Exploration Moment");

        author1.setBooks(List.of(book1));

        testEntityMgr.persist(author1);
        // when
        Optional<Author> saved = authorRepository.findById(author1.getId());
        Author actual = saved.get();
        // then
        assertNotNull(actual);
        List<Book> actualBooks = actual.getBooks();
        assertEquals(List.of(book1), actualBooks);
    }
    @Test
    @DisplayName("save should save author entity")
    void saveShouldSaveAuthorEntity() {
        // given
        Author newAuthor = new Author();
        newAuthor.setId(1L);
        newAuthor.setName("Robert Pattinson");
        // when
        Author expected = authorRepository.save(newAuthor);
        Author actual = testEntityMgr.find(Author.class, expected.getId());
        // then
        assertNotNull(actual);
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getId(), expected.getId());
    }
    @Test
    @DisplayName("should return emptyList when repository is empty")
    void shouldReturnEmptyList() {
        // given
        // when
        List<Author> actual = authorRepository.findAll();

        // then
        assertEquals(0, actual.size());
    }
}
