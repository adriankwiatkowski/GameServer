package com.example.gameserver.service;

import com.example.gameserver.dto.CategoryDto;
import com.example.gameserver.mapper.CategoryMapper;
import com.example.gameserver.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::from)
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
        var category = categoryMapper.toCategory(categoryDto);

        categoryRepository.save(category);

        return categoryMapper.from(category);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        categoryRepository.deleteById(id);
    }
}
