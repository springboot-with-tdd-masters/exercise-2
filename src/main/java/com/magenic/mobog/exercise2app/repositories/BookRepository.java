package com.magenic.mobog.exercise2app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magenic.mobog.exercise2app.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
