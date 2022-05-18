package com.magenic.mobog.exercise2app.repositories;

import com.magenic.mobog.exercise2app.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
