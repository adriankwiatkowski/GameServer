package com.example.gameserver.service;

import com.example.gameserver.model.dto.CategoryDto;
import com.example.gameserver.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CategoryDto insert(CategoryDto categoryDto) {
        categoryDto.setId(null);
        return upsert(categoryDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CategoryDto update(CategoryDto categoryDto) {
        if (!categoryRepository.existsById(categoryDto.getId())) {
            throw new EntityNotFoundException();
        }

        return upsert(categoryDto);
    }

    private CategoryDto upsert(CategoryDto categoryDto) {
        var category = CategoryDto.toCategory(categoryDto);

        categoryRepository.save(category);

        return CategoryDto.from(category);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        categoryRepository.deleteById(id);
    }
}
