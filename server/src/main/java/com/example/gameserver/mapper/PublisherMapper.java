package com.example.gameserver.mapper;

import com.example.gameserver.domain.Publisher;
import com.example.gameserver.dto.PublisherDto;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public PublisherDto from(Publisher publisher) {
        return PublisherDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }

    public Publisher toPublisher(PublisherDto publisherDto) {
        return Publisher.builder()
                .id(publisherDto.getId())
                .name(publisherDto.getName())
                .build();
    }
}