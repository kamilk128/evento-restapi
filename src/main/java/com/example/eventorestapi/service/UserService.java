package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.exceptions.UserAlreadyExistsException;
import com.example.eventorestapi.models.Event;
import com.example.eventorestapi.models.MyUser;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional
    public void registerUser(MyUser myUser) throws UserAlreadyExistsException {

        if (userRepository.existsByEmail(myUser.getEmail())) {
            throw new UserAlreadyExistsException("Email");
        }
        if (userRepository.existsByUsername(myUser.getUsername())) {
            throw new UserAlreadyExistsException("Username");
        }
        myUser.setPassword(bCryptPasswordEncoder.encode(myUser.getPassword()));

        userRepository.save(myUser);
    }

    public Optional<MyUser> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void removeUserByEmail(String email) {
        Optional<MyUser> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        MyUser user = userOpt.get();
        deleteAllEventsOfUser(user);
        userRepository.deleteByEmail(email);
    }
    @Transactional
    public void deleteAllEventsOfUser(MyUser user){
        //Iterator To Avoid ConcurrentModificationException
        Iterator<Event> authoredEventsIterator = user.getAuthoredEvents().iterator();
        while (authoredEventsIterator.hasNext()) {
            Event event = authoredEventsIterator.next();
            authoredEventsIterator.remove();
            eventService.deleteEvent(event);
        }
    }

}

