package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.models.Comment;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.User;
import com.example.eventorestapi.payload.response.CommentInListResponse;
import com.example.eventorestapi.repository.CommentRepository;
import com.example.eventorestapi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommentService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CommentRepository commentRepository;

    public List<CommentInListResponse> getComments(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        Set<Comment> commentList = event.get().getComments();
        List<CommentInListResponse> responseList = new ArrayList<>();
        for (Comment comment: commentList) {
            responseList.add(new CommentInListResponse(comment));
        }
        return responseList;
    }

    @Transactional
    public void createComment(Long eventId, User author, Comment comment) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotExistException("Event", "id");
        }
        comment.setEvent(event.get());
        comment.setAuthor(author);
        comment.setDate(new Date().getTime());
        commentRepository.saveAndFlush(comment);
    }

    @Transactional
    public void deleteComment(Comment comment) {
        comment.removeAuthor();
        commentRepository.deleteById(comment.getId());
    }

    public void deleteAllComments(Set<Comment> comments) {
        for (Comment comment: comments) {
            deleteComment(comment);
        }
        comments.clear();
    }

}
