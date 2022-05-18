package com.magenic.mobog.exercise2app.repositories;

import com.magenic.mobog.exercise2app.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.magenic.mobog.exercise2app.entities.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    List<Book> findAll();
    Page<Book> findAllByAuthorId(Long authorId, Pageable page);
    Page<Book> findAll(Pageable page);
}
