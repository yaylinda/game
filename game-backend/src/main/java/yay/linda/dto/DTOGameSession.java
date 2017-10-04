package yay.linda.dto;

import yay.linda.service.GameSession;

/**
 * DTO of a Game Session object.
 */
public class DTOGameSession {

    private Player player1;
    private Player player2;
    private GameBoard gameBoard;

    public DTOGameSession() {
    }

    public DTOGameSession(GameSession gameSession) {
        this.player1 = gameSession.getPlayer1();
        this.player2 = gameSession.getPlayer2();
        this.gameBoard = gameSession.getGameBoard();
    }

    public DTOGameSession(Player player1, Player player2, GameBoard gameBoard) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameBoard = gameBoard;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
