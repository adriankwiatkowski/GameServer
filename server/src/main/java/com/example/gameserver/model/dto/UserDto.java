package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private Integer id;

    @Size(min = 4, max = 50)
    private String username;

    @Size(min = 1, max = 80)
    private String name;

    @Size(min = 1, max = 80)
    private String surname;

    @NotNull
    private Set<RoleDto> roles;

    public static UserDto from(User user) {
        var userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setRoles(user.getRoles()
                .stream()
                .map(RoleDto::from)
                .collect(Collectors.toSet()));

        return userDto;
    }
}