package com.example.gameserver.model.dto;

import org.hibernate.validator.constraints.Length;

public record RegisterDto(
        @Length(min = 4, max = 50)
        String username,
        @Length(min = 4, max = 64)
        String password,
        @Length(min = 1, max = 80)
        String name,
        @Length(min = 1, max = 80)
        String surname
) {
}
