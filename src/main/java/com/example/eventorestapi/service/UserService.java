package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.exceptions.UserAlreadyExistsException;
import com.example.eventorestapi.models.Comment;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.User;
import com.example.eventorestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional
    public void registerUser(User user) throws UserAlreadyExistsException {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void removeUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        User user = userOpt.get();
        deleteAllEventsOfUser(user);
        deleteAllCommentsOfUser(user);
        deleteAllFriendsOfUser(user);
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public void deleteAllCommentsOfUser(User user) {
        for (Iterator<Comment> it = user.getAuthoredComments().iterator(); it.hasNext(); ) {
            Comment comment = it.next();
            it.remove();
            commentService.deleteComment(comment);
        }
    }

    @Transactional
    public void deleteAllEventsOfUser(User user) {
        for (Iterator<Event> it = user.getAuthoredEvents().iterator(); it.hasNext(); ) {
            Event event = it.next();
            it.remove();
            eventService.deleteEvent(event);
        }
    }

    @Transactional
    public void deleteAllFriendsOfUser(User user) {
        for (Iterator<User> it = user.getFriends().iterator(); it.hasNext(); ) {
            User friend = it.next();
            it.remove();
            user.removeFriend(friend);
        }
        for (Iterator<User> it = user.getFriendOf().iterator(); it.hasNext(); ) {
            User friend = it.next();
            it.remove();
            friend.removeFriend(user);
        }
        userRepository.save(user);
    }

}

