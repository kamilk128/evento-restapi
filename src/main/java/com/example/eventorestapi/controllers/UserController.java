package com.example.eventorestapi.controllers;

import com.example.eventorestapi.security.jwt.JwtUtils;
import com.example.eventorestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    JwtUtils jwtUtils;


    @DeleteMapping("/remove")
    public ResponseEntity<?> removeUser(Authentication authentication) {
        String email = authentication.getName();
        if (email != null) {
            userService.removeUserByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
