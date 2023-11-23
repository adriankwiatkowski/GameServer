package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Category;
import com.example.gameserver.model.dto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto from(Category category) {
        var categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    public Category toCategory(CategoryDto categoryDto) {
        var category = new Category();

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        return category;
    }
}