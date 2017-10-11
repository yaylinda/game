package yay.linda.dto;

import java.util.List;

public class GameSessionDTO {
    private Player player;
    private boolean myTurn;
    private List<List<Cell>> gameboard;
    private int numRows;
    private int numCols;
    private int points;

    public GameSessionDTO() {
    }

    public GameSessionDTO(Player player, boolean myTurn, GameBoard gameboard) {
        this.player = player;
        this.myTurn = myTurn;
        this.gameboard = gameboard.getBoard();
        this.numRows = gameboard.getNumRows();
        this.numCols = gameboard.getNumCols();
        this.points = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public List<List<Cell>> getGameboard() {
        return gameboard;
    }

    public void setGameboard(List<List<Cell>> gameboard) {
        this.gameboard = gameboard;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
