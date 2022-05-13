package com.example.exercisetwo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exercisetwo.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
