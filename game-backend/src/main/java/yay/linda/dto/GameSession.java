package yay.linda.dto;

import java.util.ArrayList;
import java.util.List;

public class GameSession {

    private Player player1;
    private Player player2;
    private GameBoard gameBoard;
    private List<Card> deck;
    private List<Card> discard;

    public GameSession() {}

    public GameSession(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameBoard = new GameBoard(5, 4);
        this.deck = new ArrayList<>();
        this.discard = new ArrayList<>();
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

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getDiscard() {
        return discard;
    }

    public void setDiscard(List<Card> discard) {
        this.discard = discard;
    }
}
