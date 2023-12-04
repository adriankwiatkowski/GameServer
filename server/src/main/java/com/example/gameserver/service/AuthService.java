package com.example.gameserver.service;

import com.example.gameserver.dto.LoginDto;
import com.example.gameserver.dto.RegisterDto;
import com.example.gameserver.model.MyUserDetails;

public interface AuthService {

    MyUserDetails login(LoginDto loginDto);

    void register(RegisterDto registerDto);

    void registerAdmin(RegisterDto registerDto);
}
