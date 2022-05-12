package com.magenic.mobog.exercise2app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.magenic.mobog.exercise2app.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
