package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.models.Event;

public class EventInListResponse {

    Long eventId;
    private String name;
    private String author;
    private String imageURL;
    private Long participantsNumber;
    public EventInListResponse(Event event) {
        this.eventId = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor().getUsername();
        this.imageURL = event.getImageURL();
        this.participantsNumber = event.getParticipantsNumber();
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Long getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(Long participantsNumber) {
        this.participantsNumber = participantsNumber;
    }
}
