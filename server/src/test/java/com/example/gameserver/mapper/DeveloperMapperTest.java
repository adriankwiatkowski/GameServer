package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.DeveloperEntity;
import com.example.gameserver.dto.DeveloperDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = GameServerApplication.class)
class DeveloperMapperTest {

    @Autowired
    private DeveloperMapper developerMapper;

    @Test
    public void givenDeveloper_whenMapToDeveloperDto_thenOk() {
        DeveloperEntity developerEntity = new DeveloperEntity();
        developerEntity.setId(1L);
        developerEntity.setName("Developer");

        DeveloperDto developerDto = developerMapper.from(developerEntity);

        Assertions.assertEquals(developerEntity.getId(), developerDto.getId());
        Assertions.assertEquals(developerEntity.getName(), developerDto.getName());
    }

    @Test
    public void givenDeveloperDto_whenMapToDeveloper_thenOk() {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(1L);
        developerDto.setName("Developer");

        DeveloperEntity developerEntity = developerMapper.toDeveloper(developerDto);

        Assertions.assertEquals(developerDto.getId(), developerEntity.getId());
        Assertions.assertEquals(developerDto.getName(), developerEntity.getName());
    }
}