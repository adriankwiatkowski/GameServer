package com.example.gameserver.controller;

import com.example.gameserver.dto.PlatformDto;
import com.example.gameserver.service.PlatformService;
import com.example.gameserver.util.Authority;
import com.example.gameserver.util.OpenApiUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platform")
@SecurityRequirement(name = OpenApiUtil.SCHEME_NAME)
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformService platformService;

    @GetMapping
    public List<PlatformDto> getPlatforms() {
        return platformService.getAllPlatforms();
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @PostMapping
    public PlatformDto insert(@RequestBody @Valid PlatformDto platformDto) {
        return platformService.insert(platformDto);
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @PutMapping
    public PlatformDto update(@RequestBody @Valid PlatformDto platformDto) {
        return platformService.update(platformDto);
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        platformService.deletePlatform(id);
    }
}
