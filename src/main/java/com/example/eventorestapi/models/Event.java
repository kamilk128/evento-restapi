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
    private User author;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Long startDate;

    private Long endDate;
    private double[] marker;
    private Long maxParticipantsNumber;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "events")
    private Set<User> participants = new HashSet<>();

    @OneToMany(mappedBy = "event")
    private Set<Comment> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        author.getAuthoredEvents().add(this);
        this.author = author;
    }

    public void removeAuthor() {
        this.author.getAuthoredEvents().remove(this);
        this.author = null;
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

    public int getParticipantsNumber() {
        return participants.size();
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

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addParticipant(User user) {
        participants.add(user);
        user.getEvents().add(this);
    }

    public void deleteParticipant(User user) {
        participants.remove(user);
        user.getEvents().remove(this);
    }

    public void deleteAllParticipants() {
        for (User user : participants) {
            user.getEvents().remove(this);
        }
        participants.clear();
    }

}
