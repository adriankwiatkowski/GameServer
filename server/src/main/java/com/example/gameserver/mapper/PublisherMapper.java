package com.example.gameserver.mapper;

import com.example.gameserver.domain.PublisherEntity;
import com.example.gameserver.dto.PublisherDto;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public PublisherDto from(PublisherEntity publisherEntity) {
        return PublisherDto.builder()
                .id(publisherEntity.getId())
                .name(publisherEntity.getName())
                .build();
    }

    public PublisherEntity toPublisher(PublisherDto publisherDto) {
        return PublisherEntity.builder()
                .id(publisherDto.getId())
                .name(publisherDto.getName())
                .build();
    }
}