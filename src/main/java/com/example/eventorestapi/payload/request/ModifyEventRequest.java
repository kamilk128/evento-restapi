package com.example.eventorestapi.payload.request;

import com.example.eventorestapi.models.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ModifyEventRequest {

    @NotNull(message = "id is required.")
    private Long id;
    @NotBlank(message = "name is required.")
    private String name;
    @NotBlank(message = "category is required.")
    private String category;
    @NotBlank(message = "imageUrl is required.")
    private String imageUrl;
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


    public void modifyEvent(Event event) {
        event.setName(name);
        event.setCategory(category);
        event.setImageUrl(imageUrl);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setMarker(marker);
        event.setMaxParticipantsNumber(maxParticipantsNumber);
        event.setDescription(description);
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
