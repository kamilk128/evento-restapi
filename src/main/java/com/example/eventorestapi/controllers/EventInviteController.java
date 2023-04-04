package com.example.eventorestapi.controllers;

import com.example.eventorestapi.payload.request.EventInviteRequest;
import com.example.eventorestapi.service.EventInviteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/event-invite")
public class EventInviteController {
    @Autowired
    private EventInviteService eventInviteService;

    @GetMapping("")
    public ResponseEntity<?> getUserInvitations(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(eventInviteService.getUserInvitations(authentication.getName()));
    }

    @PostMapping("")
    public ResponseEntity<?> inviteUserToEvent(Authentication authentication, @Valid @RequestBody EventInviteRequest eventInviteRequest) {
        eventInviteService.inviteUserToEvent(authentication.getName(), eventInviteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
