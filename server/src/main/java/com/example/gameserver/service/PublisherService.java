package com.example.gameserver.service;

import com.example.gameserver.model.dto.PublisherDto;
import com.example.gameserver.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(PublisherDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public PublisherDto insert(PublisherDto publisherDto) {
        publisherDto.setId(null);
        return upsert(publisherDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public PublisherDto update(PublisherDto publisherDto) {
        if (!publisherRepository.existsById(publisherDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(publisherDto);
    }

    private PublisherDto upsert(PublisherDto publisherDto) {
        var publisher = PublisherDto.toPublisher(publisherDto);

        publisherRepository.save(publisher);

        return PublisherDto.from(publisher);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deletePublisher(Long id) {
        publisherRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        publisherRepository.deleteById(id);
    }
}
