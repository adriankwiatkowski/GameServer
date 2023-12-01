package com.example.gameserver.controller;

import com.example.gameserver.dto.GenreDto;
import com.example.gameserver.service.GenreService;
import com.example.gameserver.util.Authority;
import com.example.gameserver.util.OpenApiUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@SecurityRequirement(name = OpenApiUtil.SCHEME_NAME)
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreDto> getGenres() {
        return genreService.getAllGenres();
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @PostMapping
    public GenreDto insert(@RequestBody @Valid GenreDto genreDto) {
        return genreService.insert(genreDto);
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @PutMapping
    public GenreDto update(@RequestBody @Valid GenreDto genreDto) {
        return genreService.update(genreDto);
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        genreService.deleteGenre(id);
    }
}
