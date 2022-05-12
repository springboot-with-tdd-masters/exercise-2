package com.exercise2.repository;

import com.exercise2.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCrudRepository extends JpaRepository<BookEntity, Long> {
}
