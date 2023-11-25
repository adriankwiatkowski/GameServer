package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.Genre;
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
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Gatunek");

        GenreDto genreDto = genreMapper.from(genre);

        Assertions.assertEquals(genre.getId(), genreDto.getId());
        Assertions.assertEquals(genre.getName(), genreDto.getName());
    }

    @Test
    public void givenGenreDto_whenMapToGenre_thenOk() {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(1L);
        genreDto.setName("Gatunek");

        Genre genre = genreMapper.toGenre(genreDto);

        Assertions.assertEquals(genreDto.getId(), genre.getId());
        Assertions.assertEquals(genreDto.getName(), genre.getName());
    }
}