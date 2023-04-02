package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.MyUser;
import com.example.eventorestapi.payload.response.EventInListResponse;
import com.example.eventorestapi.repository.EventRepository;
import com.example.eventorestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserEventService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    public void addParticipantToEventByEventId(String email, Long eventId) {
        Optional<MyUser> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        if (event.get().getMaxParticipantsNumber() == null || event.get().getParticipantsNumber() < event.get().getMaxParticipantsNumber()) {
            if (!event.get().getParticipants().contains(user.get())) {
                event.get().addParticipant(user.get());
                eventRepository.save(event.get());
            } else {
                throw new RuntimeException("You are already participating in this event");
            }
        } else {
            throw new RuntimeException("Too many participants already joined this event");
        }
    }

    public void deleteParticipantFromEventByEventId(String email, Long eventId) {
        Optional<MyUser> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        if (event.get().getParticipants().contains(user.get())) {
            event.get().deleteParticipant(user.get());
            eventRepository.save(event.get());
        } else {
            throw new RuntimeException("You have not yet participated in this event");
        }
    }

    public List<EventInListResponse> getUserEvents(String email) {
        Optional<MyUser> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        Set<Event> eventList = user.get().getEvents();
        List<EventInListResponse> responseList = new ArrayList<>();
        for (Event event: eventList) {
            responseList.add(new EventInListResponse(event));
        }
        return responseList;
    }
}
