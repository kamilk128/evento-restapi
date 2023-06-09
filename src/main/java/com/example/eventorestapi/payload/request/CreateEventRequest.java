package com.example.eventorestapi.payload.request;

import com.example.eventorestapi.models.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateEventRequest {
    @NotBlank(message = "name is required.")
    private String name;
    @NotBlank(message = "category is required.")
    private String category;
    @NotBlank(message = "imageUrl is required.")
    private String imageUrl;
    @NotNull(message = "startDate is required.")
    private Long startDate;
    private Long endDate;
    @NotNull(message = "marker is required.")
    private double[] marker;
    private Long maxParticipantsNumber;
    @NotBlank(message = "description is required.")
    private String description;


    public Event toEvent() {
        Event event = new Event();
        event.setName(name);
        event.setCategory(category);
        event.setImageUrl(imageUrl);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
