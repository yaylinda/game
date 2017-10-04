package yay.linda.service;

import yay.linda.config.GameConfigurations;
import yay.linda.dto.Card;
import yay.linda.dto.GameBoard;
import yay.linda.dto.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Game Session object, with logic to handle all internal states.
 */
public class GameSession {

    private GameConfigurations gameConfigurations;
    private DeckGenerator deckGenerator;

    private Player player1;
    private Player player2;
    private GameBoard gameBoard;
    private List<Card> deck;

    public GameSession() {}

    public GameSession(Player player1, Player player2, GameConfigurations gameConfigurations) {
        this.gameConfigurations = gameConfigurations;
        deckGenerator = new DeckGenerator(gameConfigurations);

        this.player1 = player1;
        this.player2 = player2;
        this.gameBoard = new GameBoard(gameConfigurations.getNumRows(), gameConfigurations.getNumCols());
        this.deck = deckGenerator.generateDeck();

        this.initializeGameState();
    }

    private void initializeGameState() {
        this.player1.setHand(pickStartingCards());
        this.player2.setHand(pickStartingCards());

    }

    private List<Card> pickStartingCards() {
        List<Card> hands = new ArrayList<>();
        for (int i = 0; i < gameConfigurations.getHandSize(); i++) {
            hands.add(this.drawCard());
        }
        return hands;
    }

    private Card drawCard() {
        int index = deckGenerator.getRandomNumberInRange(0, deck.size()-1);
        Card toReturn = deck.get(index);

        Card last = new Card(deck.get(deck.size()-1));
        deck.set(index, last);
        deck.remove(deck.size()-1);

        System.out.println("Deck now has " + deck.size() + " remaining.");

        return toReturn;
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
}
