package com.example.gameserver.service;

import com.example.gameserver.dto.GenreDto;
import com.example.gameserver.mapper.GenreMapper;
import com.example.gameserver.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(genreMapper::from)
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
            throw new EntityNotFoundException(String.format("Genre not found with id: %d", genreDto.getId()));
        }

        return upsert(genreDto);
    }

    private GenreDto upsert(GenreDto genreDto) {
        var genre = genreMapper.toGenre(genreDto);

        genreRepository.save(genre);

        return genreMapper.from(genre);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteGenre(Long id) {
        genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Genre not found with id: %d", id)));
        genreRepository.deleteById(id);
    }
}
