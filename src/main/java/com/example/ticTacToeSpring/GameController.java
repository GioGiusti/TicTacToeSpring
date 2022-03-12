package com.example.ticTacToeSpring;

import org.springframework.web.bind.annotation.*;

class GameArray {
    public CellStatus[][] gameTable = new CellStatus[3][3];
    public Player currentPlayer; // X o O

    public GameArray() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                gameTable[i][j] = CellStatus.E;

        currentPlayer = Player.X;
    }
}

@RestController
public class GameController {

    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/") //nuovo movimento e poi salvataggio nella repository
    public GameMove getGames() {
        return gameRepository.save(new GameMove());
    }

    @PostMapping("/game/create")
    public GameMove createMove(@RequestBody int i, @RequestParam int j) {
        var move = gameRepository.findTopByOrderByIdAsc().get();
        return gameRepository.save(move);
    }

}
