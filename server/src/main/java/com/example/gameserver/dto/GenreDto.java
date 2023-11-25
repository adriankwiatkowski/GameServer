package com.example.gameserver.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto implements Serializable {

    private Long id;

    @Size(max = 255)
    @NotNull(message = "Name cannot be null")
    private String name;
}