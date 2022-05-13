/**
 * 
 */
package com.training.exercise1.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.training.exercise1.exceptions.RecordNotFoundException;
import com.training.exercise1.model.Book;
import com.training.exercise1.repository.BookRepository;
import com.training.exercise1.service.impl.BookServiceImpl;

/**
 * @author michaeldelacruz
 *
 */
public class BookServiceTest {

	@InjectMocks
	private BookService bookService = new BookServiceImpl();
	
	@Mock
	private BookRepository bookRepository;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	@DisplayName("Should create record")
	public void createBookTest() {
		Book book = new Book(1L, "The Overstory", "Richard Powers");
		bookService.saveOrUpdate(book);
		verify(bookRepository, times(1)).save(book);
		
	}
	
	@Test
	@DisplayName("Should display all books")
	public void findAllBooksTest() throws RecordNotFoundException {
		List<Book> books = new ArrayList<Book>();
		Book book1 = new Book(1L, "Whereabouts", "Jhumpa Lahiri");
		Book book2 = new Book(2L, "The Bench", "Meghan Markle");
		Book book3 = new Book(3L, "Will", "Will Smith and Mark Manson");
		
		books.add(book1);
		books.add(book2);
		books.add(book3);
		
		when(bookRepository.findAll()).thenReturn(books);
		
		List<Book> bookList = bookService.findAll();
		
		assertEquals(3, bookList.size());
		verify(bookRepository, times(1)).findAll();
		
	}
	
}
