package com.example.eventorestapi.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EventInviteRequest {

    @NotNull(message = "id is required.")
    private Long id;
    @NotBlank(message = "username is required.")
    private String username;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
