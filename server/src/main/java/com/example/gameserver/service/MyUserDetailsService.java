package com.example.gameserver.service;

import com.example.gameserver.model.MyUserDetails;
import com.example.gameserver.model.dto.LoginDto;
import com.example.gameserver.model.dto.RegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsService extends UserDetailsService {

    MyUserDetails login(LoginDto loginDto) throws Exception;

    void register(RegisterDto registerDto) throws Exception;

    void registerAdmin(RegisterDto registerDto) throws Exception;
}
