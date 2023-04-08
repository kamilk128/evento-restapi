package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.security.service.UserDetailsImpl;

public class IdResponse {
    private Long id;

    public IdResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
