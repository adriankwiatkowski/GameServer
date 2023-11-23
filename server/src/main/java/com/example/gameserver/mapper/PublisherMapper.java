package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Publisher;
import com.example.gameserver.model.dto.PublisherDto;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public PublisherDto from(Publisher publisher) {
        var publisherDto = new PublisherDto();

        publisherDto.setId(publisher.getId());
        publisherDto.setName(publisher.getName());

        return publisherDto;
    }

    public Publisher toPublisher(PublisherDto publisherDto) {
        var publisher = new Publisher();

        publisher.setId(publisherDto.getId());
        publisher.setName(publisherDto.getName());

        return publisher;
    }
}