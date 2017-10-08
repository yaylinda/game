package yay.linda.service;

import yay.linda.config.GameConfigurations;
import yay.linda.dto.Card;
import yay.linda.dto.GameBoard;
import yay.linda.dto.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Game Session object, with logic to handle all internal states.
 */
public class GameSession {

    private GameConfigurations gameConfigurations;
    private DeckGenerator deckGenerator;

    private UUID id;
    private Player player1;
    private Player player2;
    private GameBoard gameBoard;
    private List<Card> deck;

    public GameSession() {
        this.id = UUID.randomUUID();
    }

    public GameSession(Player player1, Player player2, GameConfigurations gameConfigurations) {
        this.gameConfigurations = gameConfigurations;
        this.deckGenerator = new DeckGenerator(gameConfigurations);

        this.id = UUID.randomUUID();
        this.player1 = player1;
        this.player2 = player2;
        this.gameBoard = new GameBoard(gameConfigurations.getNumRows(), gameConfigurations.getNumCols());
        this.deck = deckGenerator.generateDeck();

        this.initializeGameState();
    }

    private void initializeGameState() {
        this.player1.setHand(pickStartingCards(player1.getTeam(), player1.getId().toString()));
        this.player2.setHand(pickStartingCards(player2.getTeam(), player2.getId().toString()));

    }

    private List<Card> pickStartingCards(String team, String owningPlayer) {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < gameConfigurations.getHandSize(); i++) {
            Card card = this.drawCard(owningPlayer);
            card.setOwningTeam(team);
            card.setOwningPlayer(owningPlayer);
            hand.add(card);
        }
        return hand;
    }

    public Card drawCard(String owningPlayer) {
        int index = deckGenerator.getRandomNumberInRange(0, deck.size()-1);
        Card toReturn = deck.get(index);

        Card last = new Card(deck.get(deck.size()-1));
        deck.set(index, last);
        deck.remove(deck.size()-1);

        System.out.println("Deck now has " + deck.size() + " remaining.");

        return toReturn;
    }

    public static GameSession none() {
        return new GameSession();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
