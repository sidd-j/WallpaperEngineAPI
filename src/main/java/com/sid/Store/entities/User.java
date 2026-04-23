package com.sid.Store.entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;

    private String name;
    private String email;
    private String password;
    private String userName;


    // getters & setters
}