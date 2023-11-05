package com.example.gameserver.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Publisher}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDto implements Serializable {

    private Long id;

    @Size(max = 255)
    @NotNull(message = "Name cannot be null")
    private String name;
}