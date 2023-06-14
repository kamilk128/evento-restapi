package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.exceptions.UnauthorizedException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.EventInvite;
import com.example.eventorestapi.models.User;
import com.example.eventorestapi.payload.request.ModifyEventRequest;
import com.example.eventorestapi.payload.response.EventInListResponse;
import com.example.eventorestapi.payload.response.EventInfoResponse;
import com.example.eventorestapi.payload.response.EventPageInfoResponse;
import com.example.eventorestapi.payload.response.EventPageResponse;
import com.example.eventorestapi.repository.EventInviteRepository;
import com.example.eventorestapi.repository.EventRepository;
import com.example.eventorestapi.specifications.EventSortType;
import com.example.eventorestapi.specifications.EventSpecification;
import com.example.eventorestapi.specifications.Filter;
import com.example.eventorestapi.specifications.FilterOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventInviteRepository eventInviteRepository;
    @Autowired
    private EventInviteService eventInviteService;
    @Autowired
    private CommentService commentService;

    public EventPageResponse getEvents(int pageNumber, int pageSize, String sortBy, String name, String filter, double latitude, double longitude) {
        Pageable pageRequest = PageRequest.of(pageNumber-1, pageSize);
        EventSpecification spec = new EventSpecification();
        EventSortType eventSortType = null;

        if (sortBy != null && !sortBy.isEmpty()) {
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
                case POPULARITY -> sort = Sort.by("participantsNumber").descending();
                case CLOSEST -> sort = Sort.unsorted();
                default -> {
                }
            }
            if (eventSortType == EventSortType.CLOSEST) {
                pageRequest = Pageable.unpaged();
            }
            else {
                pageRequest = ((PageRequest) pageRequest).withSort(sort);
            }
        }

        if (filter != null && !filter.isEmpty()) {
            spec.addFilter(new Filter("category", filter, FilterOperator.LIKE));
        }
        if (name != null && !name.isEmpty()) {
            spec.addFilter(new Filter("name", name, FilterOperator.LIKE));
        }

        Page<Event> eventPage;
        if (!spec.getFilters().isEmpty()) {
            eventPage = eventRepository.findAll(spec, pageRequest);
        }
        else {
            eventPage = eventRepository.findAll(pageRequest);
        }

        List<Event> eventList = new ArrayList<>(eventPage.getContent());
        if (eventSortType == EventSortType.CLOSEST) {
            final double[] location = {latitude, longitude};
            eventList.sort((o1, o2) -> distanceBetween(o1.getMarker(), location).compareTo(distanceBetween(o2.getMarker(), location)));
        }

        List<EventInListResponse> responseList = new ArrayList<>();
        for (Event event: eventList) {
            responseList.add(new EventInListResponse(event));
        }

        if (eventSortType == EventSortType.CLOSEST) {
            responseList = responseList.stream().skip((long) (pageNumber - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        }

        EventPageInfoResponse info;
        if (eventSortType == EventSortType.CLOSEST) {
            info = new EventPageInfoResponse(
                    eventPage.getTotalElements(),
                    ((int) eventPage.getTotalElements() - 1) / pageSize + 1,
                    pageNumber
            );
        }
        else {
            info = new EventPageInfoResponse(
                    eventPage.getTotalElements(),
                    eventPage.getTotalPages(),
                    eventPage.getNumber() + 1
            );
        }

        return new EventPageResponse(info, responseList);
    }

    public EventInfoResponse getEventById(String email, Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        List<EventInvite> inviteList = eventInviteRepository.findByInviteeEmailAndEventId(email, id);
        List<String> invitedBy = new ArrayList<>();
        for (EventInvite invite: inviteList) {
            invitedBy.add(invite.getInviter().getUsername());
        }
        return new EventInfoResponse(event.get(), invitedBy);
    }

    @Transactional
    public Long createEvent(User author, Event event) {
        validateEvent(event);
        event.setAuthor(author);
        Event createdEvent = eventRepository.saveAndFlush(event);
        return createdEvent.getId();
    }

    public void deleteEvent(String user, Long id) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        Event event = eventOpt.get();
        if (Objects.equals(event.getAuthor().getUsername(), user)) {
            deleteEvent(event);
        } else {
            throw new UnauthorizedException();
        }
    }
    @Transactional
    public void deleteEvent(Event event) {
        eventInviteService.deleteInvitationsToEvent(event.getId());
        commentService.deleteAllComments(event.getComments());
        event.deleteAllParticipants();
        event.removeAuthor();
        eventRepository.deleteById(event.getId());
    }

    public void modifyEvent(String user, ModifyEventRequest modifyEventRequest) {
        Optional<Event> eventOpt = eventRepository.findById(modifyEventRequest.getId());
        if (eventOpt.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        Event event = eventOpt.get();
        if (Objects.equals(event.getAuthor().getUsername(), user)) {
            modifyEventRequest.modifyEvent(event);
            validateEvent(event);
            eventRepository.save(event);
        } else {
            throw new UnauthorizedException();
        }
    }

    private void validateEvent(Event event) {
        if (event.getEndDate() != null && event.getEndDate() <= event.getStartDate()) {
            throw new RuntimeException("endDate should be later than startDate");
        }
        if (event.getMaxParticipantsNumber() != null && event.getMaxParticipantsNumber() < 1) {
            throw new RuntimeException("maxParticipantsNumber should be greater than 0");
        }
    }

    public Double distanceBetween(double[] marker1, double[] marker2) {
        double lat1 = Math.toRadians(marker1[0]);
        double lat2 = Math.toRadians(marker2[0]);
        double lng1 = Math.toRadians(marker1[1]);
        double lng2 = Math.toRadians(marker2[1]);

        double dlat = lat2 - lat1;
        double dlng = lng2 - lng1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlng / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return c * 6371;
    }
}
