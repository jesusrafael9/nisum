package com.nisum.service;

import com.nisum.model.User;
import com.nisum.repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUser(User user) {
        validate(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreated(Calendar.getInstance().getTime());
        User newUser = userRepository.save(user);
        return newUser;
    }

    public User getUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new NoSuchElementException("User does not exists");
        };
        return user.get();
    }

    private void validate(final User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (!EmailValidator.getInstance().isValid(user.getEmail())) {
            throw new IllegalArgumentException("Wrong email");
        }
    }
}
