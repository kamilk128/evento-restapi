package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.models.Event;

public class EventInListResponse {

    Long id;
    private String name;
    private String author;
    private String imageURL;
    private Long participantsNumber;
    public EventInListResponse(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor().getUsername();
        this.imageURL = event.getImageURL();
        this.participantsNumber = event.getParticipantsNumber();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
