package com.example.eventorestapi.payload.request;

import jakarta.validation.constraints.NotNull;

public class FriendRequest {

    @NotNull(message = "username is required.")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
