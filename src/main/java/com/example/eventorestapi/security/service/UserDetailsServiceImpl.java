package com.example.eventorestapi.security.service;

import com.example.eventorestapi.models.MyUser;
import com.example.eventorestapi.repository.UserRepository;
import com.example.eventorestapi.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final MyUser myUser = userRepository.findByEmail(username);
        if (myUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return UserDetailsImpl.build(myUser);
    }
}

