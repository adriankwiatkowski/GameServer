package com.example.gameserver.controller;

import com.example.gameserver.dto.PublisherDto;
import com.example.gameserver.service.PublisherService;
import com.example.gameserver.util.Authority;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<PublisherDto> getPublishers() {
        return publisherService.getAllPublishers();
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PostMapping
    public PublisherDto insert(@RequestBody @Valid PublisherDto publisherDto) {
        return publisherService.insert(publisherDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @PutMapping
    public PublisherDto update(@RequestBody @Valid PublisherDto publisherDto) {
        return publisherService.update(publisherDto);
    }

    @PreAuthorize(Authority.ADMIN_SCOPE)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        publisherService.deletePublisher(id);
    }
}
