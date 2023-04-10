package com.example.eventorestapi.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private Long dateOfBirth;

    @OneToMany(mappedBy = "author")
    private Set<Event> authoredEvents = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<Comment> authoredComments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "event_id"}))
    private Set<Event> events = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "friend_id"}))
    private Set<User> friends = new HashSet<>();

    @ManyToMany(mappedBy = "friends")
    private Set<User> friendOf = new HashSet<>();

    public User(String email, String username, String password, Long dateOfBirth) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public User() {
        this.email = "";
        this.username = "";
        this.password = "";
        this.dateOfBirth = 0L;
    }

    public void setPassword(String hashedPassword) {
        password = hashedPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String nick) {
        this.username = nick;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Set<User> getFriends() { return friends; }

    public Set<User> getFriendOf() { return friendOf; }

    public Set<Event> getAuthoredEvents() {
        return authoredEvents;
    }

    public Set<Comment> getAuthoredComments() {
        return authoredComments;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public void setFriends(Set<User> friends) { this.friends = friends; }

    public void setFriendOf(Set<User> friendOf) { this.friendOf = friendOf; }

    public void setAuthoredEvents(Set<Event> authoredEvents) {
        this.authoredEvents = authoredEvents;
    }

    public void setAuthoredComments(Set<Comment> authoredComments) {
        this.authoredComments = authoredComments;
    }

    public void addFriend(User friend) {
        friends.add(friend);
        friend.getFriendOf().add(this);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
        friend.getFriendOf().remove(this);
    }

    public boolean invitedFriend(User friend) {
        return friends.contains(friend);
    }

    public boolean participatesInEvent(Event event) {
        return events.contains(event);
    }

}
