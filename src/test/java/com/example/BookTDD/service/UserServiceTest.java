package com.example.BookTDD.service;

import com.example.BookTDD.repository.UserCrudRepository;
import com.example.BookTDD.repository.entity.UserEntity;
import com.example.BookTDD.service.adapters.UserEntityToUserAdapter;
import com.example.BookTDD.service.adapters.UserToUserEntityAdapter;
import com.example.BookTDD.service.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserToUserEntityAdapter userToUserEntityAdapter;

    @Mock
    private UserEntityToUserAdapter userEntityToUserAdapter;

    @Mock
    private UserCrudRepository userCrudRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Save user -  should accept User domain and saves it to save repository")
    public void savePositiveCase() {
        //arrange
        User user = new User("juan", "j@gmail.com");

        UserEntity mockedUserEntityFromUserDomain = mock(UserEntity.class);
        when(userToUserEntityAdapter.convert(user))
                .thenReturn(mockedUserEntityFromUserDomain);

        UserEntity savedUserFromRepository = mock(UserEntity.class);
        when(userCrudRepository.save(mockedUserEntityFromUserDomain))
                .thenReturn(savedUserFromRepository);

        User convertedUserDomainFromSavedUserInDB = mock(User.class);
        when(userEntityToUserAdapter.convert(savedUserFromRepository))
                .thenReturn(convertedUserDomainFromSavedUserInDB);


        //act
        User savedUserDomain = userService.save(user);

        //assert
        verify(userToUserEntityAdapter)
                .convert(user);

        verify(userCrudRepository)
                .save(mockedUserEntityFromUserDomain);

        verify(userEntityToUserAdapter).convert(savedUserFromRepository);

        assertThat(savedUserDomain).isEqualTo(convertedUserDomainFromSavedUserInDB);
    }
}
