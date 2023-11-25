package com.example.gameserver.mapper;

import com.example.gameserver.domain.Role;
import com.example.gameserver.dto.RoleDto;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDto from(Role role) {
        var roleDto = new RoleDto();

        roleDto.setId(role.getId());
        roleDto.setName(role.getName());

        return roleDto;
    }

    public Role toRole(RoleDto roleDto) {
        var role = new Role();

        role.setId(roleDto.getId());
        role.setName(roleDto.getName());

        return role;
    }
}