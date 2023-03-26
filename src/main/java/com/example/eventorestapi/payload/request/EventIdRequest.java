package com.example.eventorestapi.payload.request;

import jakarta.validation.constraints.NotNull;

public class EventIdRequest {
    @NotNull(message = "eventId is required.")
    private Long eventId;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
