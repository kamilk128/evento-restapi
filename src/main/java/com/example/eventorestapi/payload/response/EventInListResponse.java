package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.models.Event;

public class EventInListResponse {

    private Long id;
    private String name;
    private String author;
    private String imageUrl;
    private int participantsNumber;
    public EventInListResponse(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor().getUsername();
        this.imageUrl = event.getImageUrl();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }
}
