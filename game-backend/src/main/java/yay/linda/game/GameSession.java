package yay.linda.game;

import yay.linda.dto.Card;
import yay.linda.dto.GameBoard;
import yay.linda.dto.Player;
import yay.linda.dto.enums.GameSessionStatus;
import yay.linda.service.DeckGenerator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Game Session object, with logic to handle all internal states.
 */
public class GameSession {

    private Map<String, Player> players;

    private Map<String, GameBoard> playerGameboards;

    private Map<String, GameSessionStatus> playerGameSessionStatuses;

    private List<Card> deck;

    private int handSize;

    @Inject
    private DeckGenerator deckGenerator;

    public GameSession() { }

    public GameSession(Player player1, Player player2, int numRows, int numCols, int handSize) {
        this.players = new HashMap<>();
        this.playerGameboards = new HashMap<>();
        this.playerGameSessionStatuses = new HashMap<>();
        this.deck = this.deckGenerator.generateDeck();
        this.handSize = handSize;

        player1.setHand(this.pickStartingCards(player1.getId()));
        player2.setHand(this.pickStartingCards(player2.getId()));

        this.players.put(player1.getId(), player1);
        this.players.put(player2.getId(), player2);

        this.playerGameboards.put(player1.getId(), new GameBoard(numRows, numCols));
        this.playerGameboards.put(player2.getId(), new GameBoard(numRows, numCols));

        this.playerGameSessionStatuses.put(player1.getId(), GameSessionStatus.NEW);
        this.playerGameSessionStatuses.put(player2.getId(), GameSessionStatus.NEW);
    }

    public Card drawCard(String owningPlayerId) {
        int index = deckGenerator.getRandomNumberInRange(0, deck.size()-1);
        Card toReturn = deck.get(index);
        toReturn.setOwningPlayer(owningPlayerId);

        Card last = new Card(deck.get(deck.size()-1));
        deck.set(index, last);
        deck.remove(deck.size()-1);

        System.out.println("Deck now has " + deck.size() + " remaining.");

        return toReturn;
    }

    public void updateBoard(String playerId, GameBoard gameboard) {
        this.players.get(playerId).setPower(players.get(playerId).getPower() + 1.0);
        this.playerGameboards.put(playerId, gameboard);
        // tell other players to update
        for (String id : players.keySet()) {
            if (!id.equals(playerId)) {
                this.playerGameSessionStatuses.put(id, GameSessionStatus.NEW);
                // TODO logic to update other player's board
                this.playerGameboards.put(id, gameboard);
            }
        }
    }

    private List<Card> pickStartingCards(String owningPlayerId) {
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < handSize; i++) {
            Card card = this.drawCard(owningPlayerId);
            card.setOwningTeam(players.get(owningPlayerId).getTeam());
            card.setOwningPlayer(owningPlayerId);
            hand.add(card);
        }
        return hand;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public Map<String, GameBoard> getPlayerGameboards() {
        return playerGameboards;
    }

    public void setPlayerGameboards(Map<String, GameBoard> playerGameboards) {
        this.playerGameboards = playerGameboards;
    }

    public Map<String, GameSessionStatus> getPlayerGameSessionStatuses() {
        return playerGameSessionStatuses;
    }

    public void setPlayerGameSessionStatuses(Map<String, GameSessionStatus> playerGameSessionStatuses) {
        this.playerGameSessionStatuses = playerGameSessionStatuses;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }
}
