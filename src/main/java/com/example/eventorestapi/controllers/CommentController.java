package com.example.eventorestapi.controllers;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.models.Comment;
import com.example.eventorestapi.models.User;
import com.example.eventorestapi.payload.request.CreateCommentRequest;
import com.example.eventorestapi.service.CommentService;
import com.example.eventorestapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getComments(Authentication authentication, @PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(commentService.getComments(id));
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> addComment(Authentication authentication, @PathVariable Long id, @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        Comment comment = createCommentRequest.toComment();
        Optional<User> authorOpt = userService.getUserByEmail(authentication.getName());
        if (authorOpt.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        User author = authorOpt.get();

        commentService.createComment(id, author, comment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

