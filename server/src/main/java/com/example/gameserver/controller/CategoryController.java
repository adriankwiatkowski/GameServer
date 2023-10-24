package com.example.gameserver.controller;

import com.example.gameserver.model.Authority;
import com.example.gameserver.model.dto.CategoryDto;
import com.example.gameserver.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.getAllCategories();
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PostMapping
    public CategoryDto post(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.insert(categoryDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PutMapping
    public CategoryDto put(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }
}
