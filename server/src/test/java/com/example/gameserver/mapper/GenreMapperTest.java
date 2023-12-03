package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.GenreEntity;
import com.example.gameserver.dto.GenreDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GameServerApplication.class)
class GenreMapperTest {

    @Autowired
    private GenreMapper genreMapper;

    @Test
    public void givenEntity_whenToDto_thenOk() {
        //given
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setId(1L);
        genreEntity.setName("Gatunek");

        //when
        GenreDto genreDto = genreMapper.toDto(genreEntity);

        //then
        Assertions.assertEquals(genreEntity.getId(), genreDto.getId());
        Assertions.assertEquals(genreEntity.getName(), genreDto.getName());
    }

    @Test
    public void givenDto_whenToEntity_thenOk() {
        //given
        GenreDto genreDto = new GenreDto();
        genreDto.setId(1L);
        genreDto.setName("Gatunek");

        //when
        GenreEntity genreEntity = genreMapper.toEntity(genreDto);

        //then
        Assertions.assertEquals(genreDto.getId(), genreEntity.getId());
        Assertions.assertEquals(genreDto.getName(), genreEntity.getName());
    }
}