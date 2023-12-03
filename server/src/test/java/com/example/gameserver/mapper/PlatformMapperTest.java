package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.PlatformEntity;
import com.example.gameserver.dto.PlatformDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GameServerApplication.class)
class PlatformMapperTest {

    @Autowired
    private PlatformMapper platformMapper;

    @Test
    public void givenEntity_whenToDto_thenOk() {
        //given
        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setId(1L);
        platformEntity.setName("Platforma");

        //when
        PlatformDto platformDto = platformMapper.toDto(platformEntity);

        //then
        Assertions.assertEquals(platformEntity.getId(), platformDto.getId());
        Assertions.assertEquals(platformEntity.getName(), platformDto.getName());
    }

    @Test
    public void givenDto_whenToEntity_thenOk() {
        //given
        PlatformDto platformDto = new PlatformDto();
        platformDto.setId(1L);
        platformDto.setName("Platforma");

        //when
        PlatformEntity platformEntity = platformMapper.toEntity(platformDto);

        //then
        Assertions.assertEquals(platformDto.getId(), platformEntity.getId());
        Assertions.assertEquals(platformDto.getName(), platformEntity.getName());
    }
}