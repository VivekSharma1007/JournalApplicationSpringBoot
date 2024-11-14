package com.vivek.journalApp.controller;

import com.vivek.journalApp.entity.User;
import com.vivek.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health_check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            System.out.println(user.getUsername() + "," + user.getPassword());
            userService.saveNewUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
