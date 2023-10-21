package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Genre;
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
    private String name;

    public static GenreDto from(Genre genre) {
        var genreDto = new GenreDto();

        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());

        return genreDto;
    }
}