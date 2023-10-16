package com.example.gameserver.repository;

import com.example.gameserver.model.domain.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Optional<Developer> findByName(String name);
}