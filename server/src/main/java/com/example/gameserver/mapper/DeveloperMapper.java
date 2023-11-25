package com.example.gameserver.mapper;

import com.example.gameserver.domain.Developer;
import com.example.gameserver.dto.DeveloperDto;
import org.springframework.stereotype.Component;

@Component
public class DeveloperMapper {

    public DeveloperDto from(Developer developer) {
        return DeveloperDto.builder()
                .id(developer.getId())
                .name(developer.getName())
                .build();
    }

    public Developer toDeveloper(DeveloperDto developerDto) {
        return Developer.builder()
                .id(developerDto.getId())
                .name(developerDto.getName())
                .build();
    }
}