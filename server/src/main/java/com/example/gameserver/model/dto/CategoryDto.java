package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable {

    private Integer id;

    @Size(max = 255)
    @NotNull(message = "Name cannot be null")
    private String name;

    public static CategoryDto from(Category category) {
        var categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    public static Category toCategory(CategoryDto categoryDto) {
        var category = new Category();

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        return category;
    }
}