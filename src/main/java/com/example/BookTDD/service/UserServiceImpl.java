package com.example.BookTDD.service;

import com.example.BookTDD.repository.UserCrudRepository;
import com.example.BookTDD.repository.entity.UserEntity;
import com.example.BookTDD.service.adapters.UserEntityToUserAdapter;
import com.example.BookTDD.service.adapters.UserToUserEntityAdapter;
import com.example.BookTDD.service.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserToUserEntityAdapter userToUserEntityAdapter;

    @Autowired
    private UserEntityToUserAdapter userEntityToUserAdapter;

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Override
    public User save(User user) {

        //create mapper or adapter
        UserEntity convertedUserEntityFromUserDomain = userToUserEntityAdapter.convert(user);

        UserEntity savedUserFromDb = userCrudRepository.save(convertedUserEntityFromUserDomain);

        return userEntityToUserAdapter.convert(savedUserFromDb);
    }
}
