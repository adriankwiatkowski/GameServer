package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.User;
import com.example.gameserver.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public UserDto from(User user) {
        var userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setRoles(user.getRoles()
                .stream()
                .map(roleMapper::from)
                .collect(Collectors.toSet()));

        return userDto;
    }

    public User toUser(UserDto userDto) {
        var user = new User();

        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setRoles(userDto.getRoles().stream()
                .map(roleMapper::toRole)
                .collect(Collectors.toSet()));

        return user;
    }
}