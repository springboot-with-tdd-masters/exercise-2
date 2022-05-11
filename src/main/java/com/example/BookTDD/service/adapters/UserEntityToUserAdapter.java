package com.example.BookTDD.service.adapters;

import com.example.BookTDD.repository.entity.UserEntity;
import com.example.BookTDD.service.models.User;

public interface UserEntityToUserAdapter {
    User convert(UserEntity savedUserFromRepository);
}
