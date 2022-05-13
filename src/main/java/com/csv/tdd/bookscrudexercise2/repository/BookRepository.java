package com.csv.tdd.bookscrudexercise2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csv.tdd.bookscrudexercise2.model.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long>{

		
	
}
