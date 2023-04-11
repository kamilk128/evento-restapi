package com.example.eventorestapi.repository;

import com.example.eventorestapi.models.EventInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventInviteRepository extends JpaRepository<EventInvite, Long>, JpaSpecificationExecutor<EventInvite> {
    List<EventInvite> findByInviteeEmail(String inviteeEmail);
    List<EventInvite> findByInviteeEmailAndEventId(String inviteeEmail, Long eventId);
    void deleteByEventId(Long eventId);
    void deleteByInviteeIdOrInviterId(Long inviteeId, Long inviterId);
    boolean existsByInviteeUsernameAndEventIdAndInviterEmail(String inviteeUsername, Long eventId, String inviterEmail);
}
