package com.magenic.mobog.exercise2app.services;

import com.magenic.mobog.exercise2app.adapters.AuthorAdapter;
import com.magenic.mobog.exercise2app.adapters.BookAdapter;
import com.magenic.mobog.exercise2app.entities.Author;
import com.magenic.mobog.exercise2app.entities.Book;
import com.magenic.mobog.exercise2app.exceptions.InvalidRequestException;
import com.magenic.mobog.exercise2app.repositories.AuthorRepository;
import com.magenic.mobog.exercise2app.repositories.BookRepository;
import com.magenic.mobog.exercise2app.requests.AddBookRequest;
import com.magenic.mobog.exercise2app.requests.AddAuthorRequest;
import com.magenic.mobog.exercise2app.responses.AuthorResponse;
import com.magenic.mobog.exercise2app.responses.BookResponse;
import com.magenic.mobog.exercise2app.responses.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private BookAdapter bookAdapter;
    private AuthorAdapter authorAdapter;
    public AuthorService(AuthorRepository authorRepository,
                         BookRepository bookRepository,
                         AuthorAdapter authorAdapter,
                         BookAdapter bookAdapter){
        this.authorAdapter = authorAdapter;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.bookAdapter = bookAdapter;
    }

    public AuthorResponse createAuthor(AddAuthorRequest request){
        return Optional.ofNullable(request)
                .map(authorAdapter::mapRequestToAuthorEntity)
                .map(authorRepository::save)
                .map(authorAdapter::mapAuthorToResponse)
                .orElseThrow(InvalidRequestException::new);
    }
    public BookResponse addBookToAuthor(Long authorId, AddBookRequest addBookRequest){
        Optional<Author> foundAuthor = authorRepository.findById(authorId);
        if(foundAuthor.isPresent()){
           Book newBook = this.bookAdapter.mapRequestToEntity(addBookRequest);
           newBook.setAuthor(foundAuthor.get());
           Book saved = bookRepository.save(newBook);
           return bookAdapter.mapBookToResponse(saved);
        }
        throw new InvalidRequestException();
    }
    public PageResponse<AuthorResponse> findAuthorsWithPage(Pageable page){
        return Optional.ofNullable(this.authorRepository.findAll(page))
                .map(authorAdapter::mapPageToPageResponse)
                .orElseThrow(InvalidRequestException::new);
    }
    public PageResponse<BookResponse> findBookByAuthorWithPage(Long authorId, Pageable page){
       return Optional.ofNullable(this.bookRepository.findAllByAuthorId(authorId, page))
                .map(bookAdapter::mapPageToPageResponse)
               .orElseThrow(InvalidRequestException::new);
    }
}
