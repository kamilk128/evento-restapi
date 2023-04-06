package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.MyUser;
import com.example.eventorestapi.payload.response.EventInListResponse;
import com.example.eventorestapi.payload.response.EventPageResponse;
import com.example.eventorestapi.repository.EventInviteRepository;
import com.example.eventorestapi.repository.EventRepository;
import com.example.eventorestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserEventService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventInviteRepository eventInviteRepository;

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
                eventInviteRepository.deleteAll(eventInviteRepository.findByInviteeEmailAndEventId(email, eventId));
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

    public EventPageResponse getUserEvents(String email, int pageNumber, String name) {
        final long pageSize = 20;
        Optional<MyUser> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        Set<Event> eventList = user.get().getEvents();
        List<EventInListResponse> responseList = new ArrayList<>();
        for (Event event: eventList) {
            responseList.add(new EventInListResponse(event));
        }
        if (name != null && !name.isEmpty()) {
            responseList = responseList.stream().filter(event -> event.getName().equals(name)).collect(Collectors.toList());
        }

        Map<String, Long> info = new HashMap<>();
        info.put("results", (long) responseList.size());

        responseList = responseList.stream().skip((pageNumber-1) * pageSize).limit(pageSize).collect(Collectors.toList());

        return new EventPageResponse(info, responseList);
    }
}
