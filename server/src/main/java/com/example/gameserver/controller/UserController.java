package com.example.gameserver.controller;

import com.example.gameserver.dto.UserDto;
import com.example.gameserver.service.UserService;
import com.example.gameserver.util.Authority;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/details")
    public UserDto get() {
        return userService.getUser(getCurrentUsername());
    }

    @DeleteMapping
    public void delete() {
        userService.deleteUser(getCurrentUsername());
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
