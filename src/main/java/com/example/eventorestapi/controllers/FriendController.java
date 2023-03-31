package com.example.eventorestapi.controllers;

import com.example.eventorestapi.payload.request.FriendRequest;
import com.example.eventorestapi.service.FriendService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173", "https://evento-krqply.netlify.app"})
@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("")
    public ResponseEntity<?> getUserFriendsList(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(friendService.getFriends(authentication.getName()));
    }

    @PostMapping("")
    public ResponseEntity<?> addFriend(Authentication authentication, @Valid @RequestBody FriendRequest friendRequest) {
        friendService.addFriend(authentication.getName(), friendRequest.getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> removeFriend(Authentication authentication, @Valid @RequestBody FriendRequest friendRequest) {
        friendService.removeFriend(authentication.getName(), friendRequest.getUsername());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
