package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.model.domain.Publisher;
import com.example.gameserver.model.dto.PublisherDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = GameServerApplication.class)
class PublisherMapperTest {

    @Autowired
    private PublisherMapper publisherMapper;

    @Test
    public void givenPublisher_whenMapToPublisherDto_thenOk() {
        Publisher publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Wydawca");

        PublisherDto publisherDto = publisherMapper.from(publisher);

        Assertions.assertEquals(publisher.getId(), publisherDto.getId());
        Assertions.assertEquals(publisher.getName(), publisherDto.getName());
    }

    @Test
    public void givenPublisherDto_whenMapToPublisher_thenOk() {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setId(1L);
        publisherDto.setName("Wydawca");

        Publisher publisher = publisherMapper.toPublisher(publisherDto);

        Assertions.assertEquals(publisherDto.getId(), publisher.getId());
        Assertions.assertEquals(publisherDto.getName(), publisher.getName());
    }
}