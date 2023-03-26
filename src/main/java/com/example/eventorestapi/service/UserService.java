package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.UserAlreadyExistsException;
import com.example.eventorestapi.models.MyUser;
import com.example.eventorestapi.payload.response.UserInfoResponse;
import com.example.eventorestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerUser(MyUser myUser) throws UserAlreadyExistsException {

        if (userRepository.existsByEmail(myUser.getEmail())) {
            throw new UserAlreadyExistsException("Email already taken");
        }
        if (userRepository.existsByUsername(myUser.getUsername())) {
            throw new UserAlreadyExistsException("Username already taken");
        }
        myUser.setPassword(bCryptPasswordEncoder.encode(myUser.getPassword()));

        userRepository.save(myUser);
    }


    public UserInfoResponse getUserInfo(String email){
        return new UserInfoResponse(userRepository.findByEmail(email));
    }

    public void removeUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

}

