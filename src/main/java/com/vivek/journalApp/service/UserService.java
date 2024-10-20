package com.vivek.journalApp.service;

import com.vivek.journalApp.entity.User;
import com.vivek.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUserName(String username) {
        User savedUser = userRepository.findByUsername(username);
        return savedUser;
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

//    public User updateUser(String userName, User user) {
//        System.out.println("username " + userName);
//        List<User> userInDb = userRepository.findByUsername(userName);
//        System.out.println("userInDb " + userInDb);
//        if(userInDb != null) {
//            userInDb.get(0).setUsername(user.getUsername());
//            userInDb.get(0).setPassword(user.getPassword());
//            return userRepository.save(userInDb.get(0));
//        }
//        return new User();
//    }
}
