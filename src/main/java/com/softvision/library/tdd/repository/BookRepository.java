package com.softvision.library.tdd.repository;

import com.softvision.library.tdd.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
