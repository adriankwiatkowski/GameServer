package com.example.gameserver.repository;

import com.example.gameserver.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByOrderByNameAsc();

    List<Game> findAllByNameContainingIgnoreCaseOrderByNameAsc(String name);
}