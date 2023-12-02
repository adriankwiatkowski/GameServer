package com.example.gameserver.mapper;

import com.example.gameserver.domain.UserEntity;
import com.example.gameserver.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .roles(userEntity.getRoles().stream()
                        .map(roleMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .roles(userDto.getRoles().stream()
                        .map(roleMapper::toEntity)
                        .collect(Collectors.toSet()))
                .build();
    }
}