package com.example.eventorestapi.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private Date dateOfBirth;

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
    private Set<MyUser> friends = new HashSet<>();

    @ManyToMany(mappedBy = "friends")
    private Set<MyUser> friendOf = new HashSet<>();


    public MyUser(String email, String username, String password, Date dateOfBirth) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public MyUser() {
        this.email = "";
        this.username = "";
        this.password = "";
        this.dateOfBirth = new Date(0);
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

    public void setDateOfBirth(Date dateOfBirth) {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Set<MyUser> getFriends() { return friends; }

    public Set<MyUser> getFriendOf() { return friendOf; }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public void setFriends(Set<MyUser> friends) { this.friends = friends; }

    public void setFriendOf(Set<MyUser> friendOf) { this.friendOf = friendOf; }

    public void addFriend(MyUser friend) {
        friends.add(friend);
        friend.getFriendOf().add(this);
    }

    public void removeFriend(MyUser friend) {
        friends.remove(friend);
        friend.getFriendOf().remove(this);
    }

    public Boolean invitedFriend(MyUser friend) {
        return friends.contains(friend);
    }
}
