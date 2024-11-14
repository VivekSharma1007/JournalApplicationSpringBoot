package com.vivek.journalApp.service;

import com.vivek.journalApp.entity.User;
import com.vivek.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User findByUserName(String username) {
        User savedUser = userRepository.findByUsername(username);
        return savedUser;
    }

    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String username, User user) {
        User userInDb = userRepository.findByUsername(username);
        if (userInDb != null) {
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            return saveNewUser(userInDb);
        }
        return new User();
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
