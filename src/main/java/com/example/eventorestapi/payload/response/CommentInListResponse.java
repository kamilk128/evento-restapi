package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.models.Comment;

public class CommentInListResponse {
    
    private String content;
    private String author;
    private Long date;
    public CommentInListResponse(Comment comment) {
        this.content = comment.getContent();
        this.author = comment.getAuthor().getUsername();
        this.date = comment.getDate();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
