package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Developer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Developer}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class DeveloperDto implements Serializable {

    private Integer id;
    private String name;

    public static DeveloperDto from(Developer developer) {
        var developerDto = new DeveloperDto();

        developerDto.id(developer.getId())
                .name(developer.getName());

        return developerDto;
    }
}