package com.example.gameserver.service;

import com.example.gameserver.dto.PublisherDto;
import com.example.gameserver.mapper.PublisherMapper;
import com.example.gameserver.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(publisherMapper::toDto)
                .collect(Collectors.toList());
    }

    public PublisherDto insert(PublisherDto publisherDto) {
        publisherDto.setId(null);
        return upsert(publisherDto);
    }

    public PublisherDto update(PublisherDto publisherDto) {
        if (!publisherRepository.existsById(publisherDto.getId())) {
            throw new EntityNotFoundException(String.format("Publisher not found with id: %d", publisherDto.getId()));
        }

        return upsert(publisherDto);
    }

    private PublisherDto upsert(PublisherDto publisherDto) {
        var publisher = publisherMapper.toEntity(publisherDto);

        publisherRepository.save(publisher);

        return publisherMapper.toDto(publisher);
    }

    public void deletePublisher(Long id) {
        publisherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Publisher not found with id: %d", id)));
        publisherRepository.deleteById(id);
    }
}
