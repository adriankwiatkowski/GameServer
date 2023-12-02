package com.example.gameserver.mapper;

import com.example.gameserver.domain.PublisherEntity;
import com.example.gameserver.dto.PublisherDto;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public PublisherDto toDto(PublisherEntity publisherEntity) {
        return PublisherDto.builder()
                .id(publisherEntity.getId())
                .name(publisherEntity.getName())
                .build();
    }

    public PublisherEntity toEntity(PublisherDto publisherDto) {
        return PublisherEntity.builder()
                .id(publisherDto.getId())
                .name(publisherDto.getName())
                .build();
    }
}