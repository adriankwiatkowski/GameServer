package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.model.domain.Platform;
import com.example.gameserver.model.dto.PlatformDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = GameServerApplication.class)
class PlatformMapperTest {

    @Autowired
    private PlatformMapper platformMapper;

    @Test
    public void givenPlatform_whenMapToPlatformDto_thenOk() {
        Platform platform = new Platform();
        platform.setId(1L);
        platform.setName("Platforma");

        PlatformDto platformDto = platformMapper.from(platform);

        Assertions.assertEquals(platform.getId(), platformDto.getId());
        Assertions.assertEquals(platform.getName(), platformDto.getName());
    }

    @Test
    public void givenPlatformDto_whenMapToPlatform_thenOk() {
        PlatformDto platformDto = new PlatformDto();
        platformDto.setId(1L);
        platformDto.setName("Platforma");

        Platform platform = platformMapper.toPlatform(platformDto);

        Assertions.assertEquals(platformDto.getId(), platform.getId());
        Assertions.assertEquals(platformDto.getName(), platform.getName());
    }
}