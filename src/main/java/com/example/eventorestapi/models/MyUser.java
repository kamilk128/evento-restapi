package com.example.eventorestapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nick;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private int age;

    public MyUser(String email, String nick, String password, int age) {
        this.email = email;
        this.nick = nick;
        this.password = password;
        this.age = age;
    }

    public MyUser() {
        this.email = "";
        this.nick = "";
        this.password = "";
        this.age = 0;
    }

    public void setPassword(String hashedPassword) {
        password = hashedPassword;
    }

    public void setEmail(String Username) {
        this.email = email;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNick() {
        return nick;
    }

    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }
}
