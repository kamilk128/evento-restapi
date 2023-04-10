package com.example.eventorestapi.payload.request;

import com.example.eventorestapi.models.Comment;
import jakarta.validation.constraints.NotBlank;

public class CreateCommentRequest {
    @NotBlank(message = "content is required.")
    private String content;

    public Comment toComment() {
        Comment comment = new Comment();
        comment.setContent(content);
        return comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
