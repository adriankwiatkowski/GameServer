package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.Developer;
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
        Developer developer = new Developer();
        developer.setId(1L);
        developer.setName("Developer");
        DeveloperDto developerDto = developerMapper.from(developer);
        Assertions.assertEquals(developer.getId(), developerDto.getId());
        Assertions.assertEquals(developer.getName(), developerDto.getName());
    }

    @Test
    public void givenDeveloperDto_whenMapToDeveloper_thenOk() {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(1L);
        developerDto.setName("Developer");
        Developer developer = developerMapper.toDeveloper(developerDto);
        Assertions.assertEquals(developerDto.getId(), developer.getId());
        Assertions.assertEquals(developerDto.getName(), developer.getName());
    }
}