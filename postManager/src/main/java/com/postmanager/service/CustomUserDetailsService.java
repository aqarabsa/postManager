package com.postmanager.service;

import com.postmanager.controller.exception.UserNameAlreadyExistsException;
import com.postmanager.model.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.postmanager.repository.jpa.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    
	private UserRepository users;
    
    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Trying to pick user "+username);
        return this.users.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Error! Username: " + username + " not found"));
    }

    @Transactional
    public void createUser(String username , String password) {
        Optional<UserEntity> user = users.findByUsername(username);
        if(user.isPresent()) {
            throw new UserNameAlreadyExistsException();
        }
        UserEntity user1 = new UserEntity();
        user1.setUsername(username);
        user1.setPassword( new BCryptPasswordEncoder().encode(password));
        user1.getRoles().add("USER");
        user1 = this.users.save(user1);
    }
}