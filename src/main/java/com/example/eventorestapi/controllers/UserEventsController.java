package com.example.eventorestapi.controllers;

import com.example.eventorestapi.payload.request.EventIdRequest;
import com.example.eventorestapi.service.UserEventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173", "https://evento-krqply.netlify.app"})
@RestController
@RequestMapping("/api/user-events")
public class UserEventsController {
    @Autowired
    private UserEventService userEventService;

    @GetMapping("")
    public ResponseEntity<?> getUserEventsList(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(userEventService.getUserEvents(authentication.getName()));
    }

    @PostMapping("")
    public ResponseEntity<?> joinEvent(Authentication authentication, @Valid @RequestBody EventIdRequest eventIdRequest) {
        userEventService.addParticipantToEventByEventId(authentication.getName(), eventIdRequest.getEventId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> quitEvent(Authentication authentication, @Valid @RequestBody EventIdRequest eventIdRequest) {
        userEventService.deleteParticipantFromEventByEventId(authentication.getName(), eventIdRequest.getEventId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
