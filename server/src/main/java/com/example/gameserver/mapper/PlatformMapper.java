package com.example.gameserver.mapper;

import com.example.gameserver.domain.PlatformEntity;
import com.example.gameserver.dto.PlatformDto;
import org.springframework.stereotype.Component;

@Component
public class PlatformMapper {

    public PlatformDto from(PlatformEntity platformEntity) {
        return PlatformDto.builder()
                .id(platformEntity.getId())
                .name(platformEntity.getName())
                .build();
    }

    public PlatformEntity toPlatform(PlatformDto platformDto) {
        return PlatformEntity.builder()
                .id(platformDto.getId())
                .name(platformDto.getName())
                .build();
    }
}