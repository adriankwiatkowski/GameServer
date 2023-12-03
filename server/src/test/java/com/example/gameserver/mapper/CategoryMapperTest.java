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
        //given
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(1L);
        categoryEntity.setName("Kategoria");

        //when
        CategoryDto categoryDto = categoryMapper.toDto(categoryEntity);

        //then
        Assertions.assertEquals(categoryEntity.getId(), categoryDto.getId());
        Assertions.assertEquals(categoryEntity.getName(), categoryDto.getName());
    }

    @Test
    public void givenDto_whenToEntity_thenOk() {
        //given
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);
        categoryDto.setName("Kategoria");

        //when
        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDto);

        //then
        Assertions.assertEquals(categoryDto.getId(), categoryEntity.getId());
        Assertions.assertEquals(categoryDto.getName(), categoryEntity.getName());
    }
}
