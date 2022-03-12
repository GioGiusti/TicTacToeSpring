package com.example.ticTacToeSpring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    GameRepository gameRepository;

    public GameController(GameRepository repository) {
        this.gameRepository = repository;
    }

    @GetMapping("/startGame") //nuovo movimento e poi salvataggio nella repository
    public GameLogic getGames() {
        return new GameLogic(gameRepository.save(new GameMoveEntity()));
    }

    @PostMapping("/move/{i}/{j}")
    public GameLogic move(@PathVariable int i, @PathVariable int j) {
        var move = gameRepository.findTopByOrderByIdDesc();

        if (move.isEmpty()) throw new IllegalArgumentException("Partita inesistente");

        var newGame = new GameLogic(move.get());

        if (!newGame.isMoveValid(i, j)) throw new IllegalArgumentException("Mossa non valida");
        if (newGame.isGameOver()) throw new IllegalArgumentException("Partita terminata");

        newGame.makeMove(i, j);
        gameRepository.save(new GameMoveEntity(newGame));
        return newGame;
    }

}
