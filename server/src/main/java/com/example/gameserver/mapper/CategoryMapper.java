package com.example.gameserver.mapper;

import com.example.gameserver.model.domain.Category;
import com.example.gameserver.model.dto.CategoryDto;
import com.example.gameserver.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private final CategoryRepository categoryRepository;

    public CategoryMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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

    public Set<Category> getByIds(Set<CategoryDto> categoryDtos) {
        var categoryIds = categoryDtos.stream()
                .map(CategoryDto::getId)
                .collect(Collectors.toSet());
        return new HashSet<>(categoryRepository.findAllById(categoryIds));
    }
}