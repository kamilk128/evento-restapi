package com.example.eventorestapi.payload.response;

import java.util.List;

public class UserInfoResponse {
    private Long id;
    private String nick;
    private String email;
    private int age;
    private List<String> roles;

    public UserInfoResponse(Long id, String email, String nick, int age, List<String> roles) {
        this.id = id;
        this.nick = nick;
        this.email = email;
        this.age = age;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getAge() {
        return age;
    }
    public void setId(int age) {
        this.age = age;
    }

    public List<String> getRoles() {
        return roles;
    }
}
