package com.example.ticTacToeSpring;

import javax.persistence.*;
import java.util.Arrays;

enum CellStatus {E, X, O}

enum Player {X, O}

@Entity
public class GameMove {
    public String gameTableSerialized;
    public Player currentPlayer; // X o O
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public GameMove() {
        this.currentPlayer = Player.X;
        this.gameTableSerialized = "E,E,E;E,E,E;E,E,E";
    }

    public GameMove(GameLogic gameLogic) {
        this.currentPlayer = gameLogic.currentPlayer;
        this.gameTableSerialized = Arrays.deepToString(gameLogic.gameTable);
    }

    public Long getId() {
        return id;
    }


}
