package com.example.gameserver.service;

import com.example.gameserver.dto.LoginDto;
import com.example.gameserver.dto.RegisterDto;
import com.example.gameserver.model.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsService extends UserDetailsService {

    MyUserDetails login(LoginDto loginDto);

    void register(RegisterDto registerDto);

    void registerAdmin(RegisterDto registerDto);
}
