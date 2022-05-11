package com.example.BookTDD.repository;

import com.example.BookTDD.controller.BookController;
import com.example.BookTDD.repository.entity.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Test
    @DisplayName("Save - should accept User entity and save the correct details ")
    public void savePositiveCase() {
        //act
        UserEntity user = new UserEntity("juan", "j@gmail.com");
        UserEntity savedUser = userCrudRepository.save(user);

        //assert
        assertThat(savedUser)
                .extracting("name", "email")
                .containsExactly("juan", "j@gmail.com");
    }
}
