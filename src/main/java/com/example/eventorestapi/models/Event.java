package com.example.eventorestapi.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private MyUser author;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Long startDate;

    private Long endDate;
    private double[] marker;
    private Long participantsNumber;
    private Long maxParticipantsNumber;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "events")
    private Set<MyUser> participants = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
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

    public void setImageUrl(String imageURL) {
        this.imageUrl = imageURL;
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

    public Set<MyUser> getParticipants() {
        return participants;
    }

    public void addParticipant(MyUser user) {
        participants.add(user);
        user.getEvents().add(this);
        participantsNumber += 1;
    }

    public void deleteParticipant(MyUser user) {
        participants.remove(user);
        user.getEvents().remove(this);
        participantsNumber -= 1;
    }
}
