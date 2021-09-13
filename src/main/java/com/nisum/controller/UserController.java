package com.nisum.controller;

import com.nisum.model.User;
import com.nisum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {

    private final String PATH = "/users";
    private final String PATH_ID = PATH + "/{id}";

    @Autowired
    private UserService userService;

    @PostMapping(path = PATH, consumes = "application/json", produces = "application/json")
    public ResponseEntity addUser(@RequestBody User user) throws Exception {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping(path = PATH_ID, produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable UUID id) throws Exception {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

