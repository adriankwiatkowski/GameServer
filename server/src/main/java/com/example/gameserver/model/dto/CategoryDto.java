package com.example.gameserver.model.dto;

import com.example.gameserver.model.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.example.gameserver.model.domain.Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
public class CategoryDto implements Serializable {

    private Integer id;
    private String name;

    public static CategoryDto from(Category category) {
        var categoryDto = new CategoryDto();

        categoryDto.id(category.getId())
                .name(category.getName());

        return categoryDto;
    }
}