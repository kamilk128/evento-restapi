package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.MyUser;
import com.example.eventorestapi.payload.response.EventInListResponse;
import com.example.eventorestapi.repository.EventRepository;
import com.example.eventorestapi.repository.UserRepository;
import com.example.eventorestapi.specifications.EventSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        MyUser user = userRepository.findByEmail(email);
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            if (event.getParticipantsNumber() < event.getMaxParticipantsNumber()) {
                if (!event.getParticipants().contains(user)) {
                    event.addParticipant(user);
                    eventRepository.save(event);
                }else {
                    throw new RuntimeException("You are already participating in that event");
                }
            }else {
                throw new RuntimeException("To many participants already joined this event");
            }
        }else{
            throw new NotExistException("Event with provided id does not exist");
        }
    }

    public void deleteParticipantFromEventByEventId(String email, Long eventId) {
        MyUser user = userRepository.findByEmail(email);
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            if (event.getParticipants().contains(user)) {
                event.deleteParticipant(user);
                eventRepository.save(event);
            }else {
                throw new RuntimeException("You have not yet participated in that event");
            }
        }else{
            throw new NotExistException("Event with provided id does not exist");
        }
    }

    public List<EventInListResponse> getUserEvents(String email) {
        MyUser user = userRepository.findByEmail(email);
        Set<Event> eventList = user.getEvents();
        List<EventInListResponse> responseList = new ArrayList<EventInListResponse>();
        for (Event event: eventList) {
            responseList.add(new EventInListResponse(event));
        }
        return responseList;
    }
}
