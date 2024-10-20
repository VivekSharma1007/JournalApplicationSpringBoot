package com.vivek.journalApp.repository;

import com.vivek.journalApp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);
}
