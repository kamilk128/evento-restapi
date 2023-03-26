package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.models.MyUser;
import com.example.eventorestapi.security.service.UserDetailsImpl;

public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private Long dateOfBirth;

    public UserInfoResponse(MyUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth().getTime();
    }

    public UserInfoResponse(UserDetailsImpl userDetails) {
        this.id = userDetails.getId();
        this.username = userDetails.getUsername();
        this.email = userDetails.getNick();
        this.dateOfBirth = userDetails.getDateOfBirth().getTime();
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
}
