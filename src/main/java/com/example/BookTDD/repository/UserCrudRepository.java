package com.example.BookTDD.repository;

import com.example.BookTDD.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCrudRepository extends JpaRepository<UserEntity, Long> {
}
