package com.example.gameserver.service;

import com.example.gameserver.GameServerApplication;
import com.example.gameserver.domain.CategoryEntity;
import com.example.gameserver.dto.CategoryDto;
import com.example.gameserver.mapper.CategoryMapper;
import com.example.gameserver.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = GameServerApplication.class)
class CategoryServiceTest {

    @MockBean
    private CategoryRepository repository;

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryMapper mapper;

    @Test
    void givenCategories_whenGetAllCategories_thenHappyPath() {
        //given
        List<CategoryEntity> expectedList = getCategories();
        when(repository.findAll()).thenReturn(expectedList);

        //when
        List<CategoryDto> actualList = service.getAllCategories();

        //then
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            CategoryEntity expected = expectedList.get(0);
            CategoryDto actual = actualList.get(0);

            assertNotNull(actual);
            assertEquals(expected.getName(), actual.getName());
        }
        Mockito.verify(repository).findAll();
    }

    @Test
    void givenDtoAndMockSave_whenInsert_thenIdIsNull() {
        //given
        CategoryDto original = getCategoryDto("Category 1");
        original.setId(1L);
        when(repository.save(any(CategoryEntity.class))).thenReturn(mapper.toEntity(original));

        //when
        CategoryDto actual = service.insert(original);

        //then
        assertNull(actual.getId());
    }

    @Test
    void givenDtoAndMockSave_whenInsert_thenNameIsSame() {
        //given
        CategoryDto original = getCategoryDto("Category 1");
        original.setId(1L);
        when(repository.save(any(CategoryEntity.class))).thenReturn(mapper.toEntity(original));

        //when
        CategoryDto actual = service.insert(original);

        //then
        assertEquals("Category 1", actual.getName());
    }

    @Test
    void givenDtoAndMockSaveAndMockExistsById_whenUpdate_thenDataIsSame() {
        //given
        CategoryDto original = getCategoryDto("Category 1");
        original.setId(1L);
        when(repository.save(any(CategoryEntity.class))).thenReturn(mapper.toEntity(original));
        when(repository.existsById(1L)).thenReturn(true);

        //when
        CategoryDto actual = service.update(original);

        //then
        assertEquals(1L, actual.getId());
        assertEquals("Category 1", actual.getName());
    }

    @Test
    void givenNonExistentId_whenUpdate_thenThrowsEntityNotFoundException() {
        //given
        CategoryDto original = getCategoryDto("Category 2");
        original.setId(1L);
        when(repository.existsById(any())).thenReturn(false);

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> service.update(original));
    }

    @Test
    void givenExistentId_whenDelete_thenOk() {
        //given
        Long id = 1L;
        when(repository.findById(any())).thenReturn(Optional.of(getCategory("Category 3")));
        when(repository.existsById(any())).thenReturn(true);

        //when
        //then
        assertDoesNotThrow(() -> service.deleteCategory(id));
    }

    @Test
    void givenNonExistentId_whenDelete_thenThrowsEntityNotFoundException() {
        //given
        CategoryDto original = getCategoryDto("Category 2");
        original.setId(1L);
        when(repository.findById(any())).thenReturn(Optional.empty());

        //when
        //then
        assertThrows(EntityNotFoundException.class, () -> service.update(original));
    }

    private List<CategoryEntity> getCategories() {
        return List.of(
                getCategory("Category 1"),
                getCategory("Category 2"),
                getCategory("Category 3")
        );
    }

    private CategoryEntity getCategory(String name) {
        return CategoryEntity.builder()
                .name(name)
                .build();
    }

    private CategoryDto getCategoryDto(String name) {
        return CategoryDto.builder()
                .name(name)
                .build();
    }
}