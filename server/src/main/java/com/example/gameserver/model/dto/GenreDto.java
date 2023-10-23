package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Genre;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Genre}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto implements Serializable {

    private Integer id;

    @Size(max = 255)
    @NotNull(message = "Name cannot be null")
    private String name;

    public static GenreDto from(Genre genre) {
        var genreDto = new GenreDto();

        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());

        return genreDto;
    }

    public static Genre toGenre(GenreDto genreDto) {
        var genre = new Genre();

        genre.setId(genreDto.getId());
        genre.setName(genreDto.getName());

        return genre;
    }
}