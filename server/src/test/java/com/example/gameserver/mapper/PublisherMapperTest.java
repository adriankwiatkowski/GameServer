package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.PublisherEntity;
import com.example.gameserver.dto.PublisherDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GameServerApplication.class)
class PublisherMapperTest {

    @Autowired
    private PublisherMapper publisherMapper;

    @Test
    public void givenEntity_whenToDto_thenOk() {
        PublisherEntity publisherEntity = new PublisherEntity();
        publisherEntity.setId(1L);
        publisherEntity.setName("Wydawca");

        PublisherDto publisherDto = publisherMapper.toDto(publisherEntity);

        Assertions.assertEquals(publisherEntity.getId(), publisherDto.getId());
        Assertions.assertEquals(publisherEntity.getName(), publisherDto.getName());
    }

    @Test
    public void givenDto_whenToEntity_thenOk() {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setId(1L);
        publisherDto.setName("Wydawca");

        PublisherEntity publisherEntity = publisherMapper.toEntity(publisherDto);

        Assertions.assertEquals(publisherDto.getId(), publisherEntity.getId());
        Assertions.assertEquals(publisherDto.getName(), publisherEntity.getName());
    }
}