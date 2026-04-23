package com.sid.Store.dto;

import com.sid.Store.entities.User;

public class UserDTO {

    private String email;
    private String name;
    private String userName;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.userName = user.getUserName();
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getUserName() { return userName; }
}