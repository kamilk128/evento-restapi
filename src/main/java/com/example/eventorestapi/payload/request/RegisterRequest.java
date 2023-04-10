package com.example.eventorestapi.payload.request;

import com.example.eventorestapi.models.User;
import jakarta.validation.constraints.*;

public class RegisterRequest {
    @NotBlank(message = "email is required.")
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank(message = "username is required.")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "password is required.")
    @Size(min = 6, max = 40)
    private String password;

    @NotNull(message = "dateOfBirth is required.")
    private Long dateOfBirth;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public User toUser() {
        return new User(email, username, password, dateOfBirth);
    }
}
