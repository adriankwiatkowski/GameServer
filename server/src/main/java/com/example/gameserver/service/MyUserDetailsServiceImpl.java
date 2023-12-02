package com.example.gameserver.service;

import com.example.gameserver.domain.RoleEntity;
import com.example.gameserver.domain.UserEntity;
import com.example.gameserver.dto.LoginDto;
import com.example.gameserver.dto.RegisterDto;
import com.example.gameserver.exception.UsernameUsedException;
import com.example.gameserver.model.MyUserDetails;
import com.example.gameserver.repository.UserRepository;
import com.example.gameserver.util.Authority;
import lombok.RequiredArgsConstructor;
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
@Transactional
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));

        return new MyUserDetails(userEntity);
    }

    @Override
    public MyUserDetails login(LoginDto loginDto) {
        var user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow();
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("");
        }

        return new MyUserDetails(user);
    }

    @Override
    public void register(RegisterDto registerDto) {
        var userRole = roleService.getRoleByName(Authority.USER_ROLE);
        register(registerDto, Collections.singleton(userRole));
    }

    @Override
    public void registerAdmin(RegisterDto registerDto) {
        var userRole = roleService.getRoleByName(Authority.USER_ROLE);
        var adminRole = roleService.getRoleByName(Authority.ADMIN_ROLE);
        register(registerDto, new HashSet<>(Arrays.asList(userRole, adminRole)));
    }

    private void register(RegisterDto registerDto, Set<RoleEntity> roleEntities) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new UsernameUsedException(String.format("User already exists with username: %s", registerDto.getUsername()));
        }

        var user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setName(registerDto.getName());
        user.setSurname(registerDto.getSurname());
        user.setRoles(roleEntities);

        userRepository.saveAndFlush(user);
    }
}