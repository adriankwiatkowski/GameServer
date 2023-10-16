package com.example.gameserver.model.dto;

import org.hibernate.validator.constraints.Length;

public record LoginDto(@Length(min = 4, max = 50) String username, @Length(min = 4, max = 64) String password) {
}
