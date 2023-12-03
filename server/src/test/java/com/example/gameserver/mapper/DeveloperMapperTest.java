package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.DeveloperEntity;
import com.example.gameserver.dto.DeveloperDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GameServerApplication.class)
class DeveloperMapperTest {

    @Autowired
    private DeveloperMapper developerMapper;

    @Test
    public void givenEntity_whenToDto_thenOk() {
        //given
        DeveloperEntity developerEntity = new DeveloperEntity();
        developerEntity.setId(1L);
        developerEntity.setName("Developer");

        //when
        DeveloperDto developerDto = developerMapper.toDto(developerEntity);

        //then
        Assertions.assertEquals(developerEntity.getId(), developerDto.getId());
        Assertions.assertEquals(developerEntity.getName(), developerDto.getName());
    }

    @Test
    public void givenDto_whenToEntity_thenOk() {
        //given
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(1L);
        developerDto.setName("Developer");

        //when
        DeveloperEntity developerEntity = developerMapper.toEntity(developerDto);

        //then
        Assertions.assertEquals(developerDto.getId(), developerEntity.getId());
        Assertions.assertEquals(developerDto.getName(), developerEntity.getName());
    }
}