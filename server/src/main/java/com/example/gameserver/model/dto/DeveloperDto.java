package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Developer;
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

    private Integer id;
    private String name;

    public static DeveloperDto from(Developer developer) {
        var developerDto = new DeveloperDto();

        developerDto.setId(developer.getId());
        developerDto.setName(developer.getName());

        return developerDto;
    }
}