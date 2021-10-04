package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MyUserDetailsServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;




    @SneakyThrows
    @Test
    void registerUser() {

        // Arrange
        User user = new User("name", "password", "email", "ROLE_USER");

        // Act
        myUserDetailsService.registerUser(user);

        // Assert
        assertEquals("name", user.getUserName());
        assertEquals("password", user.getPassword());
        assertEquals("email", user.getEmail());
        assertEquals("ROLE_USER", user.getRole());

    }
}