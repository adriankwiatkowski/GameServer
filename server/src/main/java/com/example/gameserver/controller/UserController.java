package com.example.gameserver.controller;

import com.example.gameserver.dto.UserDto;
import com.example.gameserver.service.UserService;
import com.example.gameserver.util.Authority;
import com.example.gameserver.util.OpenApiUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = OpenApiUtil.SCHEME_NAME)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
