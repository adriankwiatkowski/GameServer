package com.example.gameserver.controller;

import com.example.gameserver.model.Authority;
import com.example.gameserver.model.dto.DeveloperDto;
import com.example.gameserver.service.DeveloperService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developer")
public class DeveloperController {

    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    public List<DeveloperDto> getDevelopers() {
        return developerService.getAllDevelopers();
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PostMapping
    public DeveloperDto insert(@RequestBody @Valid DeveloperDto developerDto) {
        return developerService.insert(developerDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PutMapping
    public DeveloperDto update(@RequestBody @Valid DeveloperDto developerDto) {
        return developerService.update(developerDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        developerService.deleteDeveloper(id);
    }
}
