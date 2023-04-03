package com.example.eventorestapi.controllers;

import com.example.eventorestapi.service.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user-events")
public class UserEventsController {
    @Autowired
    private UserEventService userEventService;

    @GetMapping("")
    public ResponseEntity<?> getUserEventsList(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(userEventService.getUserEvents(authentication.getName()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> joinEvent(Authentication authentication, @PathVariable Long id) {
        userEventService.addParticipantToEventByEventId(authentication.getName(), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> quitEvent(Authentication authentication, @PathVariable Long id) {
        userEventService.deleteParticipantFromEventByEventId(authentication.getName(), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
