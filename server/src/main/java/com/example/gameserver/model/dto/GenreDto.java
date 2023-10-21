package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Genre}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class GenreDto implements Serializable {

    private Integer id;
    private String name;

    public static GenreDto from(Genre genre) {
        var genreDto = new GenreDto();

        genreDto.id(genre.getId())
                .name(genre.getName());

        return genreDto;
    }
}