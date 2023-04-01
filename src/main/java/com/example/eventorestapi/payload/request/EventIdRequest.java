package com.example.eventorestapi.payload.request;

import jakarta.validation.constraints.NotNull;

public class EventIdRequest {
    @NotNull(message = "id is required.")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
