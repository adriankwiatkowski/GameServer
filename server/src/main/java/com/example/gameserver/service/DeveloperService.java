package com.example.gameserver.service;

import com.example.gameserver.model.dto.DeveloperDto;
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

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll().stream()
                .map(DeveloperDto::from)
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
        var developer = DeveloperDto.toDeveloper(developerDto);

        developerRepository.save(developer);

        return DeveloperDto.from(developer);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteDeveloper(Long id) {
        developerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        developerRepository.deleteById(id);
    }
}
