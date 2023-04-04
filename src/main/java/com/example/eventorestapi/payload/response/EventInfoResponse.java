package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.MyUser;

import java.util.List;
import java.util.stream.Collectors;

public class EventInfoResponse {

    Long id;
    private String name;
    private String author;
    private String category;
    private String imageUrl;
    private Long startDate;
    private Long endDate;
    private double[] marker;
    private Long participantsNumber;
    private Long maxParticipantsNumber;
    private String description;
    private List<String> invitedBy;
    private List<String> participantsUsernames;

    public EventInfoResponse(Event event, List<String> invitedBy) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor().getUsername();
        this.category = event.getCategory();
        this.imageUrl = event.getImageUrl();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.marker = event.getMarker();
        this.participantsNumber = event.getParticipantsNumber();
        this.maxParticipantsNumber = event.getMaxParticipantsNumber();
        this.description = event.getDescription();
        this.invitedBy = invitedBy;
        this.participantsUsernames = event.getParticipants().stream().map(MyUser::getUsername).collect(Collectors.toList());
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

    public List<String> getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(List<String> invitedBy) {
        this.invitedBy = invitedBy;
    }

    public List<String> getParticipantsUsernames() {
        return participantsUsernames;
    }

    public void setParticipantsUsernames(List<String> participantsUsernames) {
        this.participantsUsernames = participantsUsernames;
    }
}
