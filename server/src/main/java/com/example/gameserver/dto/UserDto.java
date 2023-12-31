package com.example.gameserver.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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