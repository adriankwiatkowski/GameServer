package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Developer;
import com.example.gameserver.model.dto.DeveloperDto;
import org.springframework.stereotype.Component;

@Component
public class DeveloperMapper {

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
}