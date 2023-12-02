package com.example.gameserver.service;

import com.example.gameserver.dto.GenreDto;
import com.example.gameserver.mapper.GenreMapper;
import com.example.gameserver.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    public GenreDto insert(GenreDto genreDto) {
        genreDto.setId(null);
        return upsert(genreDto);
    }

    public GenreDto update(GenreDto genreDto) {
        if (!genreRepository.existsById(genreDto.getId())) {
            throw new EntityNotFoundException(String.format("Genre not found with id: %d", genreDto.getId()));
        }

        return upsert(genreDto);
    }

    private GenreDto upsert(GenreDto genreDto) {
        var genre = genreMapper.toEntity(genreDto);

        genreRepository.save(genre);

        return genreMapper.toDto(genre);
    }

    public void deleteGenre(Long id) {
        genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Genre not found with id: %d", id)));
        genreRepository.deleteById(id);
    }
}
