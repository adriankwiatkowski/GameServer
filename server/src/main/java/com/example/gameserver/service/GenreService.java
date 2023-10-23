package com.example.gameserver.service;

import com.example.gameserver.model.dto.GenreDto;
import com.example.gameserver.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(GenreDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GenreDto insert(GenreDto genreDto) {
        genreDto.setId(null);
        return upsert(genreDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GenreDto update(GenreDto genreDto) {
        if (!genreRepository.existsById(genreDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(genreDto);
    }

    private GenreDto upsert(GenreDto genreDto) {
        var genre = GenreDto.toGenre(genreDto);

        genreRepository.save(genre);

        return GenreDto.from(genre);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGenre(Integer id) {
        genreRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        genreRepository.deleteById(id);
    }
}
