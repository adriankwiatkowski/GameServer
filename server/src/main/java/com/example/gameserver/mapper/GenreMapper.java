package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Genre;
import com.example.gameserver.model.dto.GenreDto;
import com.example.gameserver.repository.GenreRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenreMapper {

    private final GenreRepository genreRepository;

    public GenreMapper(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

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

    public Set<Genre> getByIds(Set<GenreDto> genreDtos) {
        var genreIds = genreDtos.stream()
                .map(GenreDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }
}