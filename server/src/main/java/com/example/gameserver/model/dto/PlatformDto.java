package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Platform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Platform}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformDto implements Serializable {

    private Integer id;
    private String name;

    public static PlatformDto from(Platform platform) {
        var platformDto = new PlatformDto();

        platformDto.setId(platform.getId());
        platformDto.setName(platform.getName());

        return platformDto;
    }
}