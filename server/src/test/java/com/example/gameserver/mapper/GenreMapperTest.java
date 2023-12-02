package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.GenreEntity;
import com.example.gameserver.dto.GenreDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = GameServerApplication.class)
class GenreMapperTest {

    @Autowired
    private GenreMapper genreMapper;

    @Test
    public void givenGenre_whenMapToGenreDto_thenOk() {
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setId(1L);
        genreEntity.setName("Gatunek");

        GenreDto genreDto = genreMapper.from(genreEntity);

        Assertions.assertEquals(genreEntity.getId(), genreDto.getId());
        Assertions.assertEquals(genreEntity.getName(), genreDto.getName());
    }

    @Test
    public void givenGenreDto_whenMapToGenre_thenOk() {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(1L);
        genreDto.setName("Gatunek");

        GenreEntity genreEntity = genreMapper.toGenre(genreDto);

        Assertions.assertEquals(genreDto.getId(), genreEntity.getId());
        Assertions.assertEquals(genreDto.getName(), genreEntity.getName());
    }
}