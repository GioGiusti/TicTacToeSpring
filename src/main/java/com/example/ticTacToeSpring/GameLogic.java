package com.example.ticTacToeSpring;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

class InvalidTicTacToeInput extends RuntimeException {
    InvalidTicTacToeInput(String msg) {
        super(msg);
    }
}

public class GameLogic {
    public CellStatus[][] table;
    public Player player;

    public GameLogic() {
        player = Player.X;
        table = new CellStatus[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                table[i][j] = CellStatus.E;
    }

    public GameLogic(GameMoveEntity move) {
        this.table = deserializeTable(move.table);
        this.player = move.player;
    }

    static private boolean isWinning(CellStatus c0, CellStatus c1, CellStatus c2) {
        return c0 != CellStatus.E && c0 == c1 && c1 == c2;
    }

    static private Optional<Player> getWinner(CellStatus cell) {
        return Optional.of(cell == CellStatus.X ? Player.X : Player.O);
    }

    public static String serializeTable(CellStatus[][] board) {
        return Arrays.stream(board)
                .map(r -> Arrays.stream(r).map(CellStatus::toString).collect(Collectors.joining(",")))
                .collect(Collectors.joining(";"));
    }

    public static CellStatus[][] deserializeTable(String serializedBoard) {
        return Arrays.stream(serializedBoard.split(";"))
                .map(r -> Arrays.stream(r.split(",")).map(CellStatus::valueOf).toArray(CellStatus[]::new))
                .toArray(CellStatus[][]::new);
    }

    public void makeMove(int i, int j) throws InvalidTicTacToeInput {
        if (i < 0 || i > 2 || j < 0 || j > 2) { // <- Check for out of bounds
            throw new InvalidTicTacToeInput("Out of Bounds");
        }
        if (table[i][j] != CellStatus.E) { // <- check for already filled
            throw new InvalidTicTacToeInput("Position already used");
        }

        table[i][j] = player == Player.X ? CellStatus.X : CellStatus.O;
        player = player == Player.X ? Player.O : Player.X;
    }

    public Optional<Player> getTheWinner() {
        var t = table;

        // Rows
        if (isWinning(t[0][0], t[0][1], t[0][2])) return getWinner(t[0][0]);
        if (isWinning(t[1][0], t[1][1], t[1][2])) return getWinner(t[1][0]);
        if (isWinning(t[2][0], t[2][1], t[2][2])) return getWinner(t[2][0]);

        // Columns
        if (isWinning(t[0][0], t[1][0], t[2][0])) return getWinner(t[0][0]);
        if (isWinning(t[0][1], t[1][1], t[2][1])) return getWinner(t[0][1]);
        if (isWinning(t[0][2], t[1][2], t[2][2])) return getWinner(t[0][2]);

        // Diagonals
        if (isWinning(t[0][0], t[1][1], t[2][2])) return getWinner(t[0][0]);
        if (isWinning(t[0][2], t[1][1], t[2][0])) return getWinner(t[0][2]);

        return Optional.empty();
    }

    public boolean isDraw() {
        for (var row : table)
            for (var l : row)
                if (l == CellStatus.E)
                    return false;

        return true;
    }

    public boolean isMoveValid(int i, int j) {
        return table[i][j] == CellStatus.E;
    }

    public boolean isGameOver() {
        return getTheWinner().isPresent() || isDraw();
    }
}
