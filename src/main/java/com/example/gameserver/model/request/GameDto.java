package com.example.gameserver.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link com.example.gameserver.model.domain.Game}
 */
public record GameDto(
        Integer id,
        @NotNull @Size(max = 255) String name,
        @NotNull String description,
        @NotNull LocalDate releaseDate,
        @NotNull Integer positiveRatings,
        @NotNull Integer negativeRatings,
        @NotNull Integer averagePlaytime,
        @NotNull Integer medianPlaytime,
        @NotNull @Size(max = 255) String owners,
        @NotNull BigDecimal price,
        @NotNull Set<Integer> categoryIds,
        @NotNull Set<Integer> developerIds,
        @NotNull Set<Integer> genreIds,
        @NotNull Set<Integer> platformIds,
        @NotNull Set<Integer> publisherIds
) implements Serializable {
}