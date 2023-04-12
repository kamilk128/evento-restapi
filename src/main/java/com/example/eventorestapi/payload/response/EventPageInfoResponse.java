package com.example.eventorestapi.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class EventPageInfoResponse {
    private Long results;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pages;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer currentPage;

    public EventPageInfoResponse(Long results, Integer pages, Integer currentPage) {
        this.results = results;
        this.pages = pages;
        this.currentPage = currentPage;
    }

    public EventPageInfoResponse(Long results) {
        this.results = results;
        this.pages = null;
        this.currentPage = null;
    }

    public Long getResults() {
        return results;
    }

    public void setResults(Long results) {
        this.results = results;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
