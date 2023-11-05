package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Developer;
import com.example.gameserver.model.dto.DeveloperDto;
import com.example.gameserver.repository.DeveloperRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DeveloperMapper {

    private final DeveloperRepository developerRepository;

    public DeveloperMapper(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public DeveloperDto from(Developer developer) {
        var developerDto = new DeveloperDto();

        developerDto.setId(developer.getId());
        developerDto.setName(developer.getName());

        return developerDto;
    }

    public Developer toDeveloper(DeveloperDto developerDto) {
        var developer = new Developer();

        developer.setId(developerDto.getId());
        developer.setName(developerDto.getName());

        return developer;
    }

    public Set<Developer> getByIds(Set<DeveloperDto> developerDtos) {
        var developerIds = developerDtos.stream()
                .map(DeveloperDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(developerRepository.findAllById(developerIds));
    }
}