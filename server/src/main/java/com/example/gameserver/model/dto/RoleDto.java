package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Role;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Role}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Serializable {

    private Long id;

    @Size(max = 50)
    private String name;

    public static RoleDto from(Role role) {
        var roleDto = new RoleDto();

        roleDto.setId(role.getId());
        roleDto.setName(role.getName());

        return roleDto;
    }

    public static Role toRole(RoleDto roleDto) {
        var role = new Role();

        role.setId(roleDto.getId());
        role.setName(roleDto.getName());

        return role;
    }
}