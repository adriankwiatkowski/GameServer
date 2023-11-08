package com.example.gameserver.mapper;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.model.domain.Category;
import com.example.gameserver.model.dto.CategoryDto;
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
        Category category = new Category();
        category.setId(1L);
        category.setName("Kategoria");

        CategoryDto categoryDto = categoryMapper.from(category);

        Assertions.assertEquals(category.getId(), categoryDto.getId());
        Assertions.assertEquals(category.getName(), categoryDto.getName());
    }

    @Test
    public void givenCategoryDto_whenMapToCategory_thenOk() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Kategoria");

        Category category = categoryMapper.toCategory(categoryDto);

        Assertions.assertEquals(categoryDto.getId(), category.getId());
        Assertions.assertEquals(categoryDto.getName(), category.getName());
    }
}
