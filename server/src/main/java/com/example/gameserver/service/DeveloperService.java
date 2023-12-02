package com.example.gameserver.service;

import com.example.gameserver.dto.DeveloperDto;
import com.example.gameserver.mapper.DeveloperMapper;
import com.example.gameserver.repository.DeveloperRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final DeveloperMapper developerMapper;

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll().stream()
                .map(developerMapper::toDto)
                .collect(Collectors.toList());
    }

    public DeveloperDto insert(DeveloperDto developerDto) {
        developerDto.setId(null);
        return upsert(developerDto);
    }

    public DeveloperDto update(DeveloperDto developerDto) {
        if (!developerRepository.existsById(developerDto.getId())) {
            throw new EntityNotFoundException(String.format("Developer not found with id: %d", developerDto.getId()));
        }

        return upsert(developerDto);
    }

    private DeveloperDto upsert(DeveloperDto developerDto) {
        var developer = developerMapper.toEntity(developerDto);

        developerRepository.save(developer);

        return developerMapper.toDto(developer);
    }

    public void deleteDeveloper(Long id) {
        developerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Developer not found with id: %d", id)));
        developerRepository.deleteById(id);
    }
}
