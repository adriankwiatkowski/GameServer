package com.example.client.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private Set<Role> roles;
}
//    public User(){
//        this.id = 0l;
//        this.username = "";
//        this.name = "";
//        this.surname = "";
//        this.roles = new HashSet<Role>();
//    }
//}

