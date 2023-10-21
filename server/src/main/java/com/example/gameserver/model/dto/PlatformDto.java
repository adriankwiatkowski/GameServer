package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Platform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Platform}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class PlatformDto implements Serializable {

    private Integer id;
    private String name;

    public static PlatformDto from(Platform platform) {
        var platformDto = new PlatformDto();

        platformDto.id(platform.getId())
                .name(platform.getName());

        return platformDto;
    }
}