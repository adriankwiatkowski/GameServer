package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Publisher;
import com.example.gameserver.model.dto.PublisherDto;
import com.example.gameserver.repository.PublisherRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PublisherMapper {

    private final PublisherRepository publisherRepository;

    public PublisherMapper(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

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

    public Set<Publisher> getByIds(Set<PublisherDto> publisherDtos) {
        var publisherIds = publisherDtos.stream()
                .map(PublisherDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(publisherRepository.findAllById(publisherIds));
    }
}