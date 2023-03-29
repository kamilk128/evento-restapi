package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.models.Event;

public class EventInfoResponse {

    Long eventId;
    private String name;
    private String author;
    private String category;
    private String imageURL;
    private Long startDate;
    private Long endDate;
    private double[] marker;
    private Long participantsNumber;
    private Long maxParticipantsNumber;
    private String description;
    public EventInfoResponse(Event event) {
        this.eventId = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor().getUsername();
        this.category = event.getCategory();
        this.imageURL = event.getImageURL();
        this.startDate = event.getStartDate().getTime();
        this.endDate = event.getEndDate().getTime();
        this.marker = event.getMarker();
        this.participantsNumber = event.getParticipantsNumber();
        this.maxParticipantsNumber = event.getMaxParticipantsNumber();
        this.description = event.getDescription();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public double[] getMarker() {
        return marker;
    }

    public void setMarker(double[] marker) {
        this.marker = marker;
    }

    public Long getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(Long participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public Long getMaxParticipantsNumber() {
        return maxParticipantsNumber;
    }

    public void setMaxParticipantsNumber(Long maxParticipantsNumber) {
        this.maxParticipantsNumber = maxParticipantsNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
