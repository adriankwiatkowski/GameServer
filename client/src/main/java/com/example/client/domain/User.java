package com.example.client.domain;

import lombok.Data;

import java.util.Set;

@Data
public class User {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private Set<Role> roles;
}
