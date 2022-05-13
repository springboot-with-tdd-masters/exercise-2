package com.example.exercise2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exercise2.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
