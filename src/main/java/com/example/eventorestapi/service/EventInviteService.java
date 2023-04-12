package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.EventInvite;
import com.example.eventorestapi.models.User;
import com.example.eventorestapi.payload.request.EventInviteRequest;
import com.example.eventorestapi.payload.response.EventInListResponse;
import com.example.eventorestapi.payload.response.EventPageInfoResponse;
import com.example.eventorestapi.payload.response.EventPageResponse;
import com.example.eventorestapi.repository.EventInviteRepository;
import com.example.eventorestapi.repository.EventRepository;
import com.example.eventorestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventInviteService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventInviteRepository eventInviteRepository;

    public void inviteUserToEvent(String myEmail, EventInviteRequest eventInviteRequest) {
        Optional<User> inviter = userRepository.findByEmail(myEmail);
        if (inviter.isEmpty()) {
            throw new NotExistException("Inviter", "email");
        }
        Optional<User> invitee = userRepository.findByUsername(eventInviteRequest.getUsername());
        if (invitee.isEmpty()) {
            throw new NotExistException("Invitee", "username");
        }
        if (inviter.equals(invitee)) {
            throw new RuntimeException("You cannot invite yourself to an event");
        }
        Optional<Event> event = eventRepository.findById(eventInviteRequest.getId());
        if (event.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        if (invitee.get().participatesInEvent(event.get())) {
            throw new RuntimeException("This user is already participating in this event");
        }
        if (eventInviteRepository.existsByInviteeUsernameAndEventIdAndInviterEmail(invitee.get().getUsername(), event.get().getId(), inviter.get().getEmail())) {
            throw new RuntimeException("You already invited this user to this event");
        }
        EventInvite invite = new EventInvite(inviter.get(), invitee.get(), event.get());
        eventInviteRepository.save(invite);
    }

    public EventPageResponse getUserInvitations(String email, int pageNumber, String name) {
        final long pageSize = 20;
        List<EventInvite> inviteList = eventInviteRepository.findByInviteeEmail(email);
        List<EventInListResponse> responseList = new ArrayList<>();
        for (EventInvite invite: inviteList) {
            responseList.add(new EventInListResponse(invite.getEvent()));
        }
        if (name != null && !name.isEmpty()) {
            responseList = responseList.stream().filter(event -> event.getName().equals(name)).collect(Collectors.toList());
        }

        EventPageInfoResponse info = new EventPageInfoResponse((long) responseList.size());

        responseList = responseList.stream().skip((pageNumber-1) * pageSize).limit(pageSize).collect(Collectors.toList());

        return new EventPageResponse(info, responseList);
    }

    @Transactional
    public void deleteInvitationsToEvent(Long eventId) {
        eventInviteRepository.deleteByEventId(eventId);
    }

    @Transactional
    public void deleteInvitationsOfUser(Long userId) {
        eventInviteRepository.deleteByInviteeIdOrInviterId(userId, userId);
    }
}
