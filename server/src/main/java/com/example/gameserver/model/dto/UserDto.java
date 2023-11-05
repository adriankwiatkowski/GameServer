package com.example.gameserver.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.gameserver.model.domain.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private Long id;

    @Size(min = 4, max = 50)
    private String username;

    @Size(min = 1, max = 80)
    private String name;

    @Size(min = 1, max = 80)
    private String surname;

    @NotNull
    private Set<RoleDto> roles;
}