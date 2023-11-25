package com.example.gameserver.mapper;

import com.example.gameserver.domain.Genre;
import com.example.gameserver.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDto from(Genre genre) {
        var genreDto = new GenreDto();

        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());

        return genreDto;
    }

    public Genre toGenre(GenreDto genreDto) {
        var genre = new Genre();

        genre.setId(genreDto.getId());
        genre.setName(genreDto.getName());

        return genre;
    }
}