package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Role;
import com.example.gameserver.model.dto.RoleDto;
import com.example.gameserver.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    private final RoleRepository roleRepository;

    public RoleMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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

    public Set<Role> getByIds(Set<RoleDto> roleDtos) {
        var roleIds = roleDtos.stream()
                .map(RoleDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(roleRepository.findAllById(roleIds));
    }
}