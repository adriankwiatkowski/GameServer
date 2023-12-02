package com.example.gameserver.mapper;

import com.example.gameserver.domain.RoleEntity;
import com.example.gameserver.dto.RoleDto;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto toDto(RoleEntity roleEntity) {
        return RoleDto.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();
    }

    public RoleEntity toEntity(RoleDto roleDto) {
        return RoleEntity.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .build();
    }
}