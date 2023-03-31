package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.exceptions.UnauthorizedException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.payload.request.ModifyEventRequest;
import com.example.eventorestapi.payload.response.EventInListResponse;
import com.example.eventorestapi.payload.response.EventInfoResponse;
import com.example.eventorestapi.repository.EventRepository;
import com.example.eventorestapi.specifications.EventSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<EventInListResponse> getEvents(int pageNumber, int pageSize, String sortBy, String filter) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        if (sortBy != null) {
            Sort sort = Sort.by(sortBy).ascending();
            pageRequest = pageRequest.withSort(sort);
        }

        Page<Event> eventPage;
        if (filter != null) {
            EventSpecification spec = new EventSpecification("category", filter);
            eventPage = eventRepository.findAll(spec, pageRequest);
        } else {
            eventPage = eventRepository.findAll(pageRequest);
        }
        List<Event> eventList = eventPage.getContent();


        List<EventInListResponse> responseList = new ArrayList<>();
        for (Event event: eventList) {
            responseList.add(new EventInListResponse(event));
        }
        return responseList;
    }

    public EventInfoResponse getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        return new EventInfoResponse(event.get());
    }

    public Long createEvent(Event event) {
        Event createdEvent = eventRepository.saveAndFlush(event);
        return createdEvent.getId();
    }

    public void deleteEvent(String user, Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        if (Objects.equals(event.get().getAuthor().getUsername(), user)) {
            eventRepository.deleteById(id);
        } else {
            throw new UnauthorizedException();
        }
    }

    public void modifyEvent(String user, ModifyEventRequest modifyEventRequest) {
        Optional<Event> event = eventRepository.findById(modifyEventRequest.getEventId());
        if (event.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        if (Objects.equals(event.get().getAuthor().getUsername(), user)) {
            modifyEventRequest.modifyEvent(event.get());
            eventRepository.save(event.get());
        } else {
            throw new UnauthorizedException();
        }
    }
}
