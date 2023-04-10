package com.example.eventorestapi.payload.response;

import com.example.eventorestapi.models.User;

public class FriendInListResponse {

    private String username;
    private Boolean invitationReceived;
    private Boolean invitationSent;
    public FriendInListResponse(User friend, Boolean invitationReceived, Boolean invitationSent) {
        this.username = friend.getUsername();
        this.invitationReceived = invitationReceived;
        this.invitationSent = invitationSent;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public Boolean getInvitationReceived() { return invitationReceived; }

    public void setInvitationReceived(Boolean invitationReceived) { this.invitationReceived = invitationReceived; }

    public Boolean getInvitationSent() { return invitationSent; }

    public void setInvitationSent(Boolean invitationSent) { this.invitationSent = invitationSent; }

}
