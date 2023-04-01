package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.exceptions.UnauthorizedException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.payload.request.ModifyEventRequest;
import com.example.eventorestapi.payload.response.EventInListResponse;
import com.example.eventorestapi.payload.response.EventInfoResponse;
import com.example.eventorestapi.payload.response.EventPageResponse;
import com.example.eventorestapi.repository.EventRepository;
import com.example.eventorestapi.specifications.EventSortType;
import com.example.eventorestapi.specifications.EventSpecification;
import com.example.eventorestapi.specifications.Filter;
import com.example.eventorestapi.specifications.FilterOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public EventPageResponse getEvents(int pageNumber, int pageSize, String sortBy, String name, String filter) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        EventSpecification spec = new EventSpecification();

        if (sortBy != null && !sortBy.isEmpty()) {
            EventSortType eventSortType;
            Sort sort = null;
            try {
                eventSortType = EventSortType.valueOf(sortBy.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("provided sort type is not allowed");
            }
            switch (eventSortType) {
                case SOONEST -> {
                    sort = Sort.by("startDate").ascending();
                    spec.addFilter(new Filter("startDate", new Date().getTime(), FilterOperator.GREATER_THAN));
                }
                case POPULARITY -> sort = Sort.by("participantsNumber").ascending();
                default -> {
                }
            }
            pageRequest = pageRequest.withSort(sort);
        }

        Page<Event> eventPage;
        if (filter != null && !filter.isEmpty()) {
            spec.addFilter(new Filter("category", filter));
        }
        if (name != null && !name.isEmpty()) {
            spec.addFilter(new Filter("name", name));
        }

        if (!spec.getFilters().isEmpty()){
            eventPage = eventRepository.findAll(spec, pageRequest);
        }
        else {
            eventPage = eventRepository.findAll(pageRequest);
        }

        List<Event> eventList = eventPage.getContent();
        List<EventInListResponse> responseList = new ArrayList<>();
        for (Event event: eventList) {
            responseList.add(new EventInListResponse(event));
        }


        Map<String, Long> info = new HashMap<>();
        info.put("results", eventPage.getTotalElements());
        info.put("pages", (long) eventPage.getTotalPages());
        info.put("currentPage", (long) eventPage.getNumber()+1);

        return new EventPageResponse(info, responseList);
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
        Optional<Event> event = eventRepository.findById(modifyEventRequest.getId());
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
