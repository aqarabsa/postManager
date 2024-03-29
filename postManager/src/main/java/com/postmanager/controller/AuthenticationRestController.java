package com.postmanager.controller;


import com.postmanager.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postmanager.model.dto.AuthenticationRequestDto;
import com.postmanager.repository.jpa.UserRepository;
import com.postmanager.util.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequestDto data) {

        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationRequestDto> createUser(@RequestBody AuthenticationRequestDto data)  {
        this.userDetailsService.createUser(data.getUsername(), data.getPassword());
        AuthenticationRequestDto response = new AuthenticationRequestDto();
        response.setUsername(data.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

	
}
