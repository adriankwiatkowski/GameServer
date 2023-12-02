package com.example.gameserver.repository;

import com.example.gameserver.domain.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    List<GameEntity> findAllByOrderByNameAsc();

    List<GameEntity> findAllByNameContainingIgnoreCaseOrderByNameAsc(String name);
}