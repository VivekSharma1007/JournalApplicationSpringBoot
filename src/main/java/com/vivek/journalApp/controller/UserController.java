package com.vivek.journalApp.controller;


import com.vivek.journalApp.entity.User;
import com.vivek.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAll();
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            System.out.println(user.getUsername() + "," + user.getPassword());
            userService.saveUser(user);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

//    @PutMapping("/{userName}")
//    public ResponseEntity<?> updateUser(@PathVariable String userName, @RequestBody User user) {
//        User afterSave = userService.updateUser(userName, user);
//        if(afterSave == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//    }
}
