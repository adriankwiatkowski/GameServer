package com.example.gameserver.controller;

import com.example.gameserver.dto.PublisherDto;
import com.example.gameserver.service.PublisherService;
import com.example.gameserver.util.Authority;
import com.example.gameserver.util.OpenApiUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
@SecurityRequirement(name = OpenApiUtil.SCHEME_NAME)
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    public List<PublisherDto> getPublishers() {
        return publisherService.getAllPublishers();
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @PostMapping
    public PublisherDto insert(@RequestBody @Valid PublisherDto publisherDto) {
        return publisherService.insert(publisherDto);
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @PutMapping
    public PublisherDto update(@RequestBody @Valid PublisherDto publisherDto) {
        return publisherService.update(publisherDto);
    }

    @PreAuthorize(Authority.HAS_ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        publisherService.deletePublisher(id);
    }
}
