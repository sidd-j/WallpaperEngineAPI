package com.sid.Store.dto;

import com.sid.Store.entities.User;

public class UserData {

    private String email;
    private String password;
    private String name;



    // REQUIRED: No-args constructor
    public UserData() {
    }

    // Optional: Parameterized constructor
    public UserData(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Proper getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // Setters (important for JSON binding)
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}