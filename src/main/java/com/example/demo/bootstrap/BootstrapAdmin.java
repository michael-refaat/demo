package com.example.demo.bootstrap;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapAdmin implements CommandLineRunner {

    public final UserRepository userRepository;

    public BootstrapAdmin(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        User admin = new User("admin", "admin123", "admin@test.com", "ROLE_ADMIN");

        if(userRepository.findByEmail(admin.getEmail()) == null && userRepository.findByUserName(admin.getUserName()).isEmpty()) {
            userRepository.save(admin);
        }

        System.out.println( "LIST: " + userRepository.findAll());

    }
}
