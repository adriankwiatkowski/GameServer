package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.CategoryEntity;
import com.example.gameserver.dto.CategoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = GameServerApplication.class)
class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void givenEntity_whenToDto_thenOk() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Kategoria");

        CategoryDto categoryDto = categoryMapper.toDto(categoryEntity);

        Assertions.assertEquals(categoryEntity.getId(), categoryDto.getId());
        Assertions.assertEquals(categoryEntity.getName(), categoryDto.getName());
    }

    @Test
    public void givenDto_whenToEntity_thenOk() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Kategoria");

        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDto);

        Assertions.assertEquals(categoryDto.getId(), categoryEntity.getId());
        Assertions.assertEquals(categoryDto.getName(), categoryEntity.getName());
    }
}
