package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.security.service.UserDetailsImpl;

public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private Long dateOfBirth;
    private String token;

    public UserInfoResponse(UserDetailsImpl userDetails, String token) {
        this.id = userDetails.getId();
        this.username = userDetails.getNick();
        this.email = userDetails.getUsername();
        this.dateOfBirth = userDetails.getDateOfBirth();
        this.token = token;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
