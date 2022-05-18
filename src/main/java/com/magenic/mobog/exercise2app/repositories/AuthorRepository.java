package com.magenic.mobog.exercise2app.repositories;

import com.magenic.mobog.exercise2app.entities.Author;
import com.magenic.mobog.exercise2app.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAll();
    Page<Author> findAll(Pageable page);
}
