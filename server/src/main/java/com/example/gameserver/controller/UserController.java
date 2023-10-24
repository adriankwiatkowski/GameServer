package com.example.gameserver.controller;

import com.example.gameserver.model.Authority;
import com.example.gameserver.model.MyUserDetails;
import com.example.gameserver.model.dto.UserDto;
import com.example.gameserver.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PreAuthorize(Authority.SCOPE_ADMIN)
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/details")
    public UserDto get(@AuthenticationPrincipal MyUserDetails userDetails) {
        return userService.getUser(userDetails.getUser().getId());
    }

    @DeleteMapping
    public void delete(@AuthenticationPrincipal MyUserDetails userDetails) {
        userService.deleteUser(userDetails.getUser().getId());
    }
}
