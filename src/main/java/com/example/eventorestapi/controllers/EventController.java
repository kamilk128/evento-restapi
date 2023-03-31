package com.example.eventorestapi.controllers;

import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.MyUser;
import com.example.eventorestapi.payload.request.CreateEventRequest;
import com.example.eventorestapi.payload.request.EventIdRequest;
import com.example.eventorestapi.payload.request.ModifyEventRequest;
import com.example.eventorestapi.security.service.UserDetailsImpl;
import com.example.eventorestapi.service.EventService;
import com.example.eventorestapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173", "https://evento-krqply.netlify.app"})
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @GetMapping("")
    public ResponseEntity<?> getEvents(@RequestParam(name = "page", required=false, defaultValue = "0") int page,@RequestParam(name = "pageSize", required=false, defaultValue = "10") int pageSize, @RequestParam(name = "sort-by", required=false) String sortBy, @RequestParam(name = "filter", required=false) String filter) {
        return ResponseEntity.ok(eventService.getEvents(page, pageSize, sortBy, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Long id) {
        if (id == null){
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.ok(eventService.getEventById(id));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addEvent(Authentication authentication, @Valid @RequestBody CreateEventRequest createEventRequest){
        Event event = createEventRequest.toEvent();
        MyUser author = userService.getUserByEmail(authentication.getName());
        event.setAuthor(author);
        event.setParticipantsNumber(0L);
        Long id = eventService.createEvent(event);
        Map<String, String> body = new HashMap<>();
        body.put("eventId", id.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("")
    public ResponseEntity<?> modifyEvent(Authentication authentication, @Valid @RequestBody ModifyEventRequest modifyEventRequest){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        eventService.modifyEvent(userDetails.getNick(), modifyEventRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteEvent(Authentication authentication, @Valid @RequestBody EventIdRequest eventIdRequest){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        eventService.deleteEvent(userDetails.getNick(), eventIdRequest.getEventId());
        return ResponseEntity.ok().build();
    }
}

