package com.example.gameserver.mapper;

import com.example.gameserver.domain.GenreEntity;
import com.example.gameserver.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDto toDto(GenreEntity genreEntity) {
        return GenreDto.builder()
                .id(genreEntity.getId())
                .name(genreEntity.getName())
                .build();
    }

    public GenreEntity toEntity(GenreDto genreDto) {
        return GenreEntity.builder()
                .id(genreDto.getId())
                .name(genreDto.getName())
                .build();
    }
}