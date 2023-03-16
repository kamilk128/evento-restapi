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
            throw new UserAlreadyExistsException("Username already taken");
        }
        if (userRepository.existsByNick(myUser.getNick())) {
            throw new UserAlreadyExistsException("Email already taken");
        }
        myUser.setPassword(bCryptPasswordEncoder.encode(myUser.getPassword()));

        userRepository.save(myUser);
    }


    public UserInfoResponse getUserInfo(String username){
        MyUser dbUser = userRepository.findByEmail(username);
        return new UserInfoResponse(dbUser.getId(), dbUser.getEmail(), dbUser.getNick(), dbUser.getAge(), Collections.emptyList());
    }

    public void removeUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

}

