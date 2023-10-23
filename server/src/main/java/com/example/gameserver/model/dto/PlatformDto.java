package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Platform;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Size(max = 255)
    @NotNull(message = "Name cannot be null")
    private String name;

    public static PlatformDto from(Platform platform) {
        var platformDto = new PlatformDto();

        platformDto.setId(platform.getId());
        platformDto.setName(platform.getName());

        return platformDto;
    }

    public static Platform toPlatform(PlatformDto platformDto) {
        var platform = new Platform();

        platform.setId(platformDto.getId());
        platform.setName(platformDto.getName());

        return platform;
    }
}