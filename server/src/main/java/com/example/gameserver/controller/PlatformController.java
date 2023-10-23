package com.example.gameserver.controller;

import com.example.gameserver.model.Authority;
import com.example.gameserver.model.dto.PlatformDto;
import com.example.gameserver.service.PlatformService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platform")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public List<PlatformDto> getPlatforms() {
        return platformService.getAllPlatforms();
    }

    @PreAuthorize(Authority.SCOPE_ADMIN)
    @PostMapping
    public PlatformDto post(@RequestBody @Valid PlatformDto platformDto) {
        return platformService.insert(platformDto);
    }

    @PreAuthorize(Authority.SCOPE_ADMIN)
    @PutMapping
    public PlatformDto put(@RequestBody @Valid PlatformDto platformDto) {
        return platformService.update(platformDto);
    }

    @PreAuthorize(Authority.SCOPE_ADMIN)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        platformService.deletePlatform(id);
    }
}
