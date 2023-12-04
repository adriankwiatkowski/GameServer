package com.example.gameserver.controller;

import com.example.gameserver.dto.LoginDto;
import com.example.gameserver.dto.RegisterDto;
import com.example.gameserver.dto.TokenDto;
import com.example.gameserver.service.AuthService;
import com.example.gameserver.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody @Valid LoginDto loginDto) {
        LOG.debug("login");

        var user = authService.login(loginDto);
        var token = tokenService.generateToken(user);

        return new TokenDto(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {
        LOG.debug("register");
        authService.register(registerDto);
        return ResponseEntity.ok().build();
    }
}
