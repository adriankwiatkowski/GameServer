package com.example.gameserver.model.request;

import org.hibernate.validator.constraints.Length;

public record LoginRequest(@Length(min = 4, max = 50) String username, @Length(min = 4, max = 64) String password) {
}
