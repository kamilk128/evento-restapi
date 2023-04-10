package com.example.eventorestapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User author;

    @Column(nullable = false)
    private Long date;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        author.getAuthoredComments().add(this);
        this.author = author;
    }

    public void removeAuthor() {
        this.author.getAuthoredComments().remove(this);
        this.author = null;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

}
