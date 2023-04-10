package com.example.eventorestapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "event_invites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"inviter_id", "invitee_id", "event_id"}))
public class EventInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inviter_id", referencedColumnName = "id")
    private User inviter;

    @ManyToOne
    @JoinColumn(name = "invitee_id", referencedColumnName = "id")
    private User invitee;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    public EventInvite(User inviter, User invitee, Event event) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.event = event;
    }

    public EventInvite() {
    }

    public Long getId() {
        return id;
    }

    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public User getInvitee() {
        return invitee;
    }

    public void setInvitee(User invitee) {
        this.invitee = invitee;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
