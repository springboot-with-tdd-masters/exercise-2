package com.softvision.library.tdd.service;

import com.softvision.library.tdd.model.Book;
import com.softvision.library.tdd.model.RecordNotFoundException;
import com.softvision.library.tdd.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository repository;

    @Override
    public Book createOrUpdate(final Book entity) {
        return repository.findById(entity.getId()).map(entityToBeUpdated -> {
            entityToBeUpdated.setAuthor(entity.getAuthor());
            entityToBeUpdated.setTitle(entity.getTitle());

            entityToBeUpdated = repository.save(entityToBeUpdated);
            return entityToBeUpdated;
        }).orElseGet(() -> repository.save(entity));
    }

    @Override
    public List<Book> getAll() {
        return repository.findAll();
    }

}
