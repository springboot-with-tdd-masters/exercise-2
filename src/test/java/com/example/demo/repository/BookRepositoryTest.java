package com.example.demo.repository;

import com.example.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindAllBooks(){
        List<Book> retrievedBooks = bookRepository.findAll();
        assertEquals(2, retrievedBooks.size());
    }

    @Test
    public void testFindById(){
        Book savedBook = bookRepository.save(Book.builder().title("title1").author("author1").build());
        Book savedBook2 = bookRepository.save(Book.builder().title("title2").author("author2").build());
        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());
        assertEquals(savedBook.getTitle(), retrievedBook.get().getTitle());
        assertNotEquals(savedBook2.getAuthor(), retrievedBook.get().getAuthor());
    }

    @Test
    public void testDeleteById(){
        Book savedBook = bookRepository.findById(1L).get();
        bookRepository.deleteById(savedBook.getId());
        List<Book> retrievedBooks = bookRepository.findAll();
        assertEquals(1, retrievedBooks.size());
    }

    @Test
    public void testUpdateBook(){
        Book savedBook = bookRepository.save(Book.builder().title("title1").author("author1").build());
        Optional<Book> retrievedBook = bookRepository.findById(savedBook.getId());
        retrievedBook.get().setAuthor("newAuthor");
        Book updatedBook = bookRepository.save(retrievedBook.get());
        assertEquals("newAuthor", updatedBook.getAuthor());
    }
}