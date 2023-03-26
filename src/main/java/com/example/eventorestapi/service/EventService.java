package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.exceptions.UnauthorizedException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.payload.request.ModifyEventRequest;
import com.example.eventorestapi.payload.response.EventInListResponse;
import com.example.eventorestapi.payload.response.EventInfoResponse;
import com.example.eventorestapi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<EventInListResponse> getEvents(int page, String sortBy, String filter) {
        List<Event> eventList = eventRepository.findAll();
        List<EventInListResponse> responseList = new ArrayList<EventInListResponse>();
        for (Event event: eventList) {
            responseList.add(new EventInListResponse(event));
        }
        return responseList;
    }

    public EventInfoResponse getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return new EventInfoResponse(event.get());
        }else{
            throw new NotExistException("Event with provided id does not exist");
        }
    }

    public Long createEvent(Event event) {
        Event createdEvent = eventRepository.saveAndFlush(event);
        return createdEvent.getId();
    }

    public void deleteEvent(String user, Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            if (Objects.equals(event.get().getAuthor(), user)){
                eventRepository.deleteById(id);
            }else {
                throw new UnauthorizedException("You don't have permission to modify this content");
            }
        }else{
            throw new NotExistException("Event with provided id does not exist");
        }
    }

    public void modifyEvent(String user, ModifyEventRequest modifyEventRequest) {
        Optional<Event> eventOpt = eventRepository.findById(modifyEventRequest.getEventId());
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            if (Objects.equals(event.getAuthor(), user)){
                modifyEventRequest.modifyEvent(event);
                eventRepository.save(event);
            }else {
                throw new UnauthorizedException("You don't have permission to modify this content");
            }
        }else{
            throw new NotExistException("Event with provided id does not exist");
        }
    }
}
