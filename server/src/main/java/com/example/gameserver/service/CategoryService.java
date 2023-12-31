package com.example.gameserver.service;

import com.example.gameserver.dto.CategoryDto;
import com.example.gameserver.mapper.CategoryMapper;
import com.example.gameserver.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDto insert(CategoryDto categoryDto) {
        categoryDto.setId(null);
        return upsert(categoryDto);
    }

    public CategoryDto update(CategoryDto categoryDto) {
        ensureCategoryExists(categoryDto.getId());
        return upsert(categoryDto);
    }

    private CategoryDto upsert(CategoryDto categoryDto) {
        var category = categoryMapper.toEntity(categoryDto);

        categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    public void deleteCategory(Long id) {
        ensureCategoryExists(id);
        categoryRepository.deleteById(id);
    }

    private void ensureCategoryExists(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Category not found with id: %d", id));
        }
    }
}
