package com.example.ticTacToeSpring;

import javax.persistence.*;

enum CellStatus {E, X, O}

enum Player {X, O}

@Entity
@Table(name = "gameTable")
public class GameMoveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    public String table;
    public Player player;

    public GameMoveEntity() {
        this(new GameLogic());
    }

    public GameMoveEntity(GameLogic game) {
        this.player = game.player;
        this.table = GameLogic.serializeTable(game.table);
    }

    public Long getId() {
        return id;
    }
}
