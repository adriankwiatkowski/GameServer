package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Developer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Developer}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeveloperDto implements Serializable {

    private Long id;

    @Size(max = 255)
    @NotNull(message = "Name cannot be null")
    private String name;

    public static DeveloperDto from(Developer developer) {
        var developerDto = new DeveloperDto();

        developerDto.setId(developer.getId());
        developerDto.setName(developer.getName());

        return developerDto;
    }

    public static Developer toDeveloper(DeveloperDto developerDto) {
        var developer = new Developer();

        developer.setId(developerDto.getId());
        developer.setName(developerDto.getName());

        return developer;
    }
}