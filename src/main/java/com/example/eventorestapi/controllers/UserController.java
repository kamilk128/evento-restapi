package com.example.eventorestapi.controllers;

import com.example.eventorestapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/info")
    public ResponseEntity<?> userInfo(Authentication authentication) {
        String username = authentication.getName();
        if (username!=null) {
            return ResponseEntity.ok(userService.getUserInfo(username));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeUser(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        String email = authentication.getName();
        if (email != null) {
            userService.removeUserByEmail(email);
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
