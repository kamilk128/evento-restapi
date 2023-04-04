package com.example.eventorestapi.repository;

import com.example.eventorestapi.models.EventInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventInviteRepository extends JpaRepository<EventInvite, Long>, JpaSpecificationExecutor<EventInvite> {
    List<EventInvite> findByInviteeEmail(String email);
    List<EventInvite> findByInviteeEmailAndEventId(String email, Long id);

    boolean existsByInviteeUsernameAndEventIdAndInviterEmail(String invitee_username, Long event_id, String inviter_email);
}
