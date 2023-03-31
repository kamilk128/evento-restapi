package com.example.eventorestapi.service;

import com.example.eventorestapi.exceptions.NotExistException;
import com.example.eventorestapi.models.MyUser;
import com.example.eventorestapi.payload.response.FriendInListResponse;
import com.example.eventorestapi.repository.EventRepository;
import com.example.eventorestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FriendService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;

    public void addFriend(String myEmail, String friendUsername) {
        Optional<MyUser> user = userRepository.findByEmail(myEmail);
        if (user.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        Optional<MyUser> friend = userRepository.findByUsername(friendUsername);
        if (friend.isEmpty()) {
            throw new NotExistException("User", "username");
        }
        if (user.equals(friend)) {
            throw new RuntimeException("You cannot add yourself to friends");
        }
        if (user.get().invitedFriend(friend.get())) {
            if (friend.get().invitedFriend(user.get())) {
                throw new RuntimeException("You are already friends");
            } else {
                throw new RuntimeException("You already sent this user a friend request");
            }
        }
        user.get().addFriend(friend.get());
        userRepository.save(user.get());
    }

    public void removeFriend(String myEmail, String friendUsername) {
        Optional<MyUser> user = userRepository.findByEmail(myEmail);
        if (user.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        Optional<MyUser> friend = userRepository.findByUsername(friendUsername);
        if (friend.isEmpty()) {
            throw new NotExistException("User", "username");
        }
        if (user.equals(friend)) {
            throw new RuntimeException("You cannot remove yourself from friends");
        }
        if (!user.get().invitedFriend(friend.get())) {
            throw new RuntimeException("You have not sent this user a friend request");
        }
        user.get().removeFriend(friend.get());
        userRepository.save(user.get());
    }

    public List<FriendInListResponse> getFriends(String email) {
        Optional<MyUser> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new NotExistException("User", "email");
        }
        Set<MyUser> friendList = user.get().getFriends();
        Set<MyUser> friendOfList = user.get().getFriendOf();
        Set<MyUser> mutualFriendList = new HashSet<>(friendList);
        mutualFriendList.retainAll(friendOfList);
        friendList.removeAll(mutualFriendList);
        friendOfList.removeAll(mutualFriendList);
        List<FriendInListResponse> responseList = new ArrayList<>();
        for (MyUser friend: mutualFriendList) {
            responseList.add(new FriendInListResponse(friend, true, true));
        }
        for (MyUser friend: friendList) {
            responseList.add(new FriendInListResponse(friend, false, true));
        }
        for (MyUser friendOf: friendOfList) {
            responseList.add(new FriendInListResponse(friendOf, true, false));
        }
        return responseList;
    }
}
