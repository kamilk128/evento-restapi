package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.models.EventInvite;

public class EventIdInListResponse {

    Long id;
    public EventIdInListResponse(EventInvite eventInvite) {
        this.id = eventInvite.getEvent().getId();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
