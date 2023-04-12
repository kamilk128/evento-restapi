package com.example.eventorestapi.payload.response;

import java.util.List;
import java.util.Map;

public class EventPageResponse {
    private EventPageInfoResponse info;
    private List<EventInListResponse> eventsList;

    public EventPageResponse(EventPageInfoResponse info, List<EventInListResponse> eventsList) {
        this.info = info;
        this.eventsList = eventsList;
    }

    public EventPageInfoResponse getInfo() {
        return info;
    }

    public void setInfo(EventPageInfoResponse info) {
        this.info = info;
    }

    public List<EventInListResponse> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<EventInListResponse> eventsList) {
        this.eventsList = eventsList;
    }
}
