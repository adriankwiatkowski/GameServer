package com.example.gameserver.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link com.example.gameserver.model.domain.Game}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto implements Serializable {

    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(max = 255)
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @NotNull
    @PositiveOrZero
    private Integer positiveRatings;

    @NotNull
    @PositiveOrZero
    private Integer negativeRatings;

    @NotNull
    @PositiveOrZero
    private Integer averagePlaytime;

    @NotNull
    @PositiveOrZero
    private Integer medianPlaytime;

    @NotNull
    @Size(max = 255)
    @NotBlank
    @Size(max = 255)
    private String owners;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull(message = "Categories cannot be null")
    private Set<CategoryDto> categories;

    @NotNull
    private Set<DeveloperDto> developers;

    @NotNull
    private Set<GenreDto> genres;

    @NotNull
    private Set<PlatformDto> platforms;

    @NotNull
    private Set<PublisherDto> publishers;
}