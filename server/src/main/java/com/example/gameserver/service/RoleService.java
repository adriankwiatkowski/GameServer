package com.example.gameserver.service;

import com.example.gameserver.model.domain.Role;
import com.example.gameserver.model.dto.RoleDto;
import com.example.gameserver.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public RoleDto insert(RoleDto roleDto) {
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
        var role = RoleDto.toRole(roleDto);

        roleRepository.save(role);

        return RoleDto.from(role);
    }
}
