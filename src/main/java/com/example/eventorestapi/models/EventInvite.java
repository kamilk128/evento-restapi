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
    private MyUser inviter;

    @ManyToOne
    @JoinColumn(name = "invitee_id", referencedColumnName = "id")
    private MyUser invitee;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    public EventInvite(MyUser inviter, MyUser invitee, Event event) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.event = event;
    }

    public EventInvite() {
    }

    public Long getId() {
        return id;
    }

    public MyUser getInviter() {
        return inviter;
    }

    public void setInviter(MyUser inviter) {
        this.inviter = inviter;
    }

    public MyUser getInvitee() {
        return invitee;
    }

    public void setInvitee(MyUser invitee) {
        this.invitee = invitee;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
