package com.example.eventorestapi.controllers;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.User;
import com.example.eventorestapi.payload.request.CreateEventRequest;
import com.example.eventorestapi.payload.request.ModifyEventRequest;
import com.example.eventorestapi.payload.response.IdResponse;
import com.example.eventorestapi.security.service.UserDetailsImpl;
import com.example.eventorestapi.service.EventService;
import com.example.eventorestapi.service.UserEventService;
import com.example.eventorestapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserEventService userEventService;

    @GetMapping("")
    public ResponseEntity<?> getEvents(@RequestParam(name = "page", required=false, defaultValue = "1") int page, @RequestParam(name = "pageSize", required=false, defaultValue = "20") int pageSize, @RequestParam(name = "sort-by", required=false) String sortBy, @RequestParam(name = "name", required=false) String name, @RequestParam(name = "filter", required=false) String filter) {
        return ResponseEntity.ok(eventService.getEvents(page, pageSize, sortBy, name, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(Authentication authentication, @PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(eventService.getEventById(authentication.getName(), id));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addEvent(Authentication authentication, @Valid @RequestBody CreateEventRequest createEventRequest) {
        Event event = createEventRequest.toEvent();
        Optional<User> authorOpt = userService.getUserByEmail(authentication.getName());
        if (authorOpt.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        User author = authorOpt.get();

        Long id = eventService.createEvent(author, event);
        userEventService.addParticipantToEventByEventId(authentication.getName(), id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(id));
    }

    @PutMapping("")
    public ResponseEntity<?> modifyEvent(Authentication authentication, @Valid @RequestBody ModifyEventRequest modifyEventRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        eventService.modifyEvent(userDetails.getNick(), modifyEventRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(Authentication authentication, @PathVariable Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        eventService.deleteEvent(userDetails.getNick(), id);
        return ResponseEntity.ok().build();
    }
}

