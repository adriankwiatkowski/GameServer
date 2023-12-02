package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.CategoryEntity;
import com.example.gameserver.dto.CategoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = GameServerApplication.class)
class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void givenCategory_whenMapToCategoryDto_thenOk() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Kategoria");

        CategoryDto categoryDto = categoryMapper.from(categoryEntity);

        Assertions.assertEquals(categoryEntity.getId(), categoryDto.getId());
        Assertions.assertEquals(categoryEntity.getName(), categoryDto.getName());
    }

    @Test
    public void givenCategoryDto_whenMapToCategory_thenOk() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Kategoria");

        CategoryEntity categoryEntity = categoryMapper.toCategory(categoryDto);

        Assertions.assertEquals(categoryDto.getId(), categoryEntity.getId());
        Assertions.assertEquals(categoryDto.getName(), categoryEntity.getName());
    }
}
