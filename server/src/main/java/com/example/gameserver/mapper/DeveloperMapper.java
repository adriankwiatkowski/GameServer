package com.example.gameserver.mapper;

import com.example.gameserver.domain.DeveloperEntity;
import com.example.gameserver.dto.DeveloperDto;
import org.springframework.stereotype.Component;

@Component
public class DeveloperMapper {

    public DeveloperDto from(DeveloperEntity developerEntity) {
        return DeveloperDto.builder()
                .id(developerEntity.getId())
                .name(developerEntity.getName())
                .build();
    }

    public DeveloperEntity toDeveloper(DeveloperDto developerDto) {
        return DeveloperEntity.builder()
                .id(developerDto.getId())
                .name(developerDto.getName())
                .build();
    }
}