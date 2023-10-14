package com.example.gameserver.service;

import com.example.gameserver.model.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsService extends UserDetailsService {

    MyUserDetails login(String username, String password) throws Exception;

    void register(String username, String password) throws Exception;
}
