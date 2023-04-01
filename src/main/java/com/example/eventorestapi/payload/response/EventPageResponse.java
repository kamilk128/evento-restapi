package com.example.eventorestapi.payload.response;

import java.util.List;
import java.util.Map;

public class EventPageResponse {
    private Map<String, Long> info;
    private List<EventInListResponse> eventsList;

    public EventPageResponse(Map<String, Long> info, List<EventInListResponse> eventsList) {
        this.info = info;
        this.eventsList = eventsList;
    }

    public Map<String, Long> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Long> info) {
        this.info = info;
    }

    public List<EventInListResponse> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<EventInListResponse> eventsList) {
        this.eventsList = eventsList;
    }
}
