package com.example.gameserver.mapper;

import com.example.gameserver.domain.User;
import com.example.gameserver.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .roles(user.getRoles().stream()
                        .map(roleMapper::from)
                        .collect(Collectors.toSet()))
                .build();
    }

    public User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .roles(userDto.getRoles().stream()
                        .map(roleMapper::toRole)
                        .collect(Collectors.toSet()))
                .build();
    }
}