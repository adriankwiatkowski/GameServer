package com.example.gameserver.repository;

import com.example.gameserver.model.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformRepository extends JpaRepository<Platform, Integer> {
    Optional<Platform> findByName(String name);
}