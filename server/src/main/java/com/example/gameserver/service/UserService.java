package com.example.gameserver.service;

import com.example.gameserver.model.dto.UserDto;
import com.example.gameserver.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public UserDto getUser(String username) {
        return userRepository.findByUsername(username)
                .map(UserDto::from)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteUser(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.deleteById(user.getId());
    }
}