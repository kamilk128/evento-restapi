package com.example.eventorestapi.controllers;

import com.example.eventorestapi.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("")
    public ResponseEntity<?> getUserFriendsList(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(friendService.getFriends(authentication.getName()));
    }

    @PostMapping("/{username}")
    public ResponseEntity<?> addFriend(Authentication authentication, @PathVariable String username) {
        friendService.addFriend(authentication.getName(), username);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> removeFriend(Authentication authentication, @PathVariable String username) {
        friendService.removeFriend(authentication.getName(), username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
