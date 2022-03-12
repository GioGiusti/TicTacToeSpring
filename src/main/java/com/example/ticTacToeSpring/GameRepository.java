package com.example.ticTacToeSpring;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<GameMove, Long> {
    Optional<GameMove> findTopByOrderByIdAsc();


}
