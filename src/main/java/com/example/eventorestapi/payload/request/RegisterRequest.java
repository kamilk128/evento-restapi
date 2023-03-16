package com.example.eventorestapi.payload.request;

import com.example.eventorestapi.models.MyUser;
import jakarta.validation.constraints.*;

public class RegisterRequest {
    @NotBlank(message = "username is required.")
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank(message = "nick is required.")
    @Size(min = 3, max = 20)
    private String nick;

    @NotBlank(message = "password is required.")
    @Size(min = 6, max = 40)
    private String password;

    private int age;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MyUser toUser() {
        return new MyUser(email, nick, password, age);
    }
}
