package com.example.demo.service;

import com.example.demo.configuration.security.MyUserDetails;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserName(userName);
        return user.map(MyUserDetails::new).get();

    }


    public User registerUser(User user) {

        User n = new User(user.getUserName(), user.getPassword(), user.getEmail(), "ROLE_USER");

        if(userRepository.findByEmail(n.getEmail()) == null && userRepository.findByUserName(n.getUserName()).isEmpty()) {

            return userRepository.save(n);

        }

        return null;

    }


}
