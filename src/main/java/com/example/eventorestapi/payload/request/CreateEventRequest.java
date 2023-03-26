package com.example.eventorestapi.payload.request;

import com.example.eventorestapi.models.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CreateEventRequest {
    @NotBlank(message = "name is required.")
    private String name;
    @NotBlank(message = "category is required.")
    private String category;
    @NotBlank(message = "imageURL is required.")
    private String imageURL;
    @NotNull(message = "startDate is required.")
    private Long startDate;
    @NotNull(message = "endDate is required.")
    private Long endDate;
    @NotNull(message = "marker is required.")
    private double[] marker;
    @NotNull(message = "maxParticipantsNumber is required.")
    private Long maxParticipantsNumber;
    @NotBlank(message = "description is required.")
    private String description;


    public Event toEvent(){
        Event event = new Event();
        event.setName(name);
        event.setCategory(category);
        event.setImageURL(imageURL);
        event.setStartDate(new Date(startDate));
        event.setEndDate(new Date(endDate));
        event.setMarker(marker);
        event.setMaxParticipantsNumber(maxParticipantsNumber);
        event.setDescription(description);
        return event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
