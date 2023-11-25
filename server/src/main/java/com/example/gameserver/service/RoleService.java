package com.example.gameserver.service;

import com.example.gameserver.domain.Role;
import com.example.gameserver.dto.RoleDto;
import com.example.gameserver.mapper.RoleMapper;
import com.example.gameserver.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public RoleDto insert(RoleDto roleDto) throws Exception {
        if (roleRepository.existsByName(roleDto.getName())) {
            throw new Exception(String.format("Role already exists with name: %s", roleDto.getName()));
        }

        roleDto.setId(null);
        return upsert(roleDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public RoleDto update(RoleDto roleDto) {
        if (!roleRepository.existsById(roleDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(roleDto);
    }

    private RoleDto upsert(RoleDto roleDto) {
        var role = roleMapper.toRole(roleDto);

        roleRepository.saveAndFlush(role);

        return roleMapper.from(role);
    }
}
