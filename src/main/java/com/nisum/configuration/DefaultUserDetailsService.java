package com.nisum.configuration;

import com.nisum.model.User;
import com.nisum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> appUser = userRepository.findByEmail(username);
        if(appUser.isPresent()){
            return toUserDetails(appUser.get());
        }
        return null;
    }

    private UserDetails toUserDetails(User userObject) {
        return org.springframework.security.core.userdetails.User.withUsername(userObject.getEmail())
                .password(userObject.getPassword())
                .authorities(new ArrayList<>()).build();
    }
}
