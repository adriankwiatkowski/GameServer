package com.example.gameserver.controller;

import com.example.gameserver.dto.CategoryDto;
import com.example.gameserver.service.CategoryService;
import com.example.gameserver.util.Authority;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.getAllCategories();
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PostMapping
    public CategoryDto insert(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.insert(categoryDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PutMapping
    public CategoryDto update(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
