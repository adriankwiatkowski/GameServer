package com.example.gameserver.controller;

import com.example.gameserver.dto.GenreDto;
import com.example.gameserver.service.GenreService;
import com.example.gameserver.util.Authority;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDto> getGenres() {
        return genreService.getAllGenres();
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PostMapping
    public GenreDto insert(@RequestBody @Valid GenreDto genreDto) {
        return genreService.insert(genreDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PutMapping
    public GenreDto update(@RequestBody @Valid GenreDto genreDto) {
        return genreService.update(genreDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        genreService.deleteGenre(id);
    }
}
