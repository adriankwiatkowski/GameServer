package com.example.gameserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto implements Serializable {

    @Length(min = 4, max = 50)
    private String username;

    @Length(min = 4, max = 64)
    private String password;

    @Length(min = 1, max = 80)
    private String name;

    @Length(min = 1, max = 80)
    private String surname;
}