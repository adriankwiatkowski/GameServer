package com.example.gameserver.controller;

import com.example.gameserver.model.dto.LoginDto;
import com.example.gameserver.service.MyUserDetailsService;
import com.example.gameserver.service.TokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final MyUserDetailsService myUserDetailsService;
    private final TokenService tokenService;

    public AuthController(MyUserDetailsService myUserDetailsService, TokenService tokenService) {
        this.myUserDetailsService = myUserDetailsService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) {
        LOG.debug("login");
        try {
            var user = myUserDetailsService.login(loginDto.username(), loginDto.password());
            var token = tokenService.generateToken(user);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid LoginDto loginDto) {
        LOG.debug("register");
        try {
            myUserDetailsService.register(loginDto.username(), loginDto.password());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}