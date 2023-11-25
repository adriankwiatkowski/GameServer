package com.example.gameserver.service;

import com.example.gameserver.dto.DeveloperDto;
import com.example.gameserver.mapper.DeveloperMapper;
import com.example.gameserver.repository.DeveloperRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final DeveloperMapper developerMapper;

    public DeveloperService(DeveloperRepository developerRepository, DeveloperMapper developerMapper) {
        this.developerRepository = developerRepository;
        this.developerMapper = developerMapper;
    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll().stream()
                .map(developerMapper::from)
                .collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public DeveloperDto insert(DeveloperDto developerDto) {
        developerDto.setId(null);
        return upsert(developerDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public DeveloperDto update(DeveloperDto developerDto) {
        if (!developerRepository.existsById(developerDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(developerDto);
    }

    private DeveloperDto upsert(DeveloperDto developerDto) {
        var developer = developerMapper.toDeveloper(developerDto);

        developerRepository.save(developer);

        return developerMapper.from(developer);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteDeveloper(Long id) {
        developerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        developerRepository.deleteById(id);
    }
}
