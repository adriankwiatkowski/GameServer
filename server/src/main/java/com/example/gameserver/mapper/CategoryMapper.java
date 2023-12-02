package com.example.gameserver.mapper;

import com.example.gameserver.domain.CategoryEntity;
import com.example.gameserver.dto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(CategoryEntity categoryEntity) {
        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .build();
    }

    public CategoryEntity toEntity(CategoryDto categoryDto) {
        return CategoryEntity.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}