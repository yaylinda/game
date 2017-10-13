package yay.linda.dto;

import yay.linda.dto.enums.GameState;

import java.util.List;

/**
 * Represents a GameSession object for one player.
 */
public class GameSessionDTO {
    private Player player;
    private boolean myTurn;
    private List<List<Cell>> gameboard;
    private int numRows;
    private int numCols;
    private String state;
    private int numCardsInDeck;

    public GameSessionDTO() {
    }

    public GameSessionDTO(Player player, GameBoard gameboard, String gameState, int numCardsInDeck, boolean myTurn) {
        this.player = player;
        this.gameboard = gameboard.getBoard();
        this.state = gameState;
        this.myTurn = myTurn;
        this.numRows = gameboard.getNumRows();
        this.numCols = gameboard.getNumCols();
        this.numCardsInDeck = numCardsInDeck;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getNumCardsInDeck() {
        return numCardsInDeck;
    }

    public void setNumCardsInDeck(int numCardsInDeck) {
        this.numCardsInDeck = numCardsInDeck;
    }
}
