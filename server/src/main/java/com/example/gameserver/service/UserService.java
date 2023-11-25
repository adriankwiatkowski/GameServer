package com.example.gameserver.service;

import com.example.gameserver.dto.UserDto;
import com.example.gameserver.mapper.UserMapper;
import com.example.gameserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::from)
                .collect(Collectors.toList());
    }

    public UserDto getUser(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::from)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User not found with username: %s", username)));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteUser(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User not found with username: %s", username)));
        userRepository.deleteById(user.getId());
    }
}
