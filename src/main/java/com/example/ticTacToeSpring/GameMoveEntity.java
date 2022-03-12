package com.example.ticTacToeSpring;

import javax.persistence.*;

enum CellStatus {E, X, O}

enum Player {X, O}

@Entity
public class GameMoveEntity {
    public String table;
    public Player player; // X o O

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    public GameMoveEntity() {
        this(new GameLogic());
    }

    public GameMoveEntity(GameLogic game) {
        this.player = game.player;
        this.table = game.tableSerialize(table);
    }

    public Long getId() {
        return id;
    }
}
