package com.example.exercise2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.exercise2.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

}
