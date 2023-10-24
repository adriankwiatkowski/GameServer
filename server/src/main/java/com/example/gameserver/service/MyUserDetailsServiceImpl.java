package com.example.gameserver.service;

import com.example.gameserver.model.Authority;
import com.example.gameserver.model.MyUserDetails;
import com.example.gameserver.model.domain.Role;
import com.example.gameserver.model.domain.User;
import com.example.gameserver.model.dto.LoginDto;
import com.example.gameserver.model.dto.RegisterDto;
import com.example.gameserver.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public MyUserDetailsServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));

        return new MyUserDetails(user);
    }

    @Transactional
    @Override
    public MyUserDetails login(LoginDto loginDto) {
        var user = userRepository.findByUsername(loginDto.username()).orElseThrow();
        if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            throw new BadCredentialsException("");
        }

        return new MyUserDetails(user);
    }

    @Transactional
    @Override
    public void register(RegisterDto registerDto) throws Exception {
        var userRole = roleService.getRoleByName(Authority.ROLE_USER);
        register(registerDto, Collections.singleton(userRole));
    }

    @Transactional
    @Override
    public void registerAdmin(RegisterDto registerDto) throws Exception {
        var userRole = roleService.getRoleByName(Authority.ROLE_USER);
        var adminRole = roleService.getRoleByName(Authority.ROLE_ADMIN);
        register(registerDto, new HashSet<>(Arrays.asList(userRole, adminRole)));
    }

    private void register(RegisterDto registerDto, Set<Role> roles) throws Exception {
        if (userRepository.findByUsername(registerDto.username()).isPresent()) {
            throw new Exception(String.format("User already exists with username: %s", registerDto.username()));
        }

        var user = new User();
        user.setUsername(registerDto.username());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        user.setName(registerDto.name());
        user.setSurname(registerDto.surname());
        user.setRoles(roles);

        userRepository.saveAndFlush(user);
    }
}