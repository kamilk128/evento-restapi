package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.EventInvite;
import com.example.eventorestapi.models.MyUser;
import com.example.eventorestapi.payload.request.EventInviteRequest;
import com.example.eventorestapi.payload.response.EventIdInListResponse;
import com.example.eventorestapi.repository.EventInviteRepository;
import com.example.eventorestapi.repository.EventRepository;
import com.example.eventorestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventInviteService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventInviteRepository eventInviteRepository;

    public void inviteUserToEvent(String myEmail, EventInviteRequest eventInviteRequest) {
        Optional<MyUser> inviter = userRepository.findByEmail(myEmail);
        if (inviter.isEmpty()) {
            throw new NotExistException("Inviter", "email");
        }
        Optional<MyUser> invitee = userRepository.findByUsername(eventInviteRequest.getUsername());
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

    public List<EventIdInListResponse> getUserInvitations(String email) {
        List<EventInvite> inviteList = eventInviteRepository.findByInviteeEmail(email);
        List<EventIdInListResponse> responseList = new ArrayList<>();
        for (EventInvite invite: inviteList) {
            responseList.add(new EventIdInListResponse(invite));
        }
        return responseList;
    }

}
